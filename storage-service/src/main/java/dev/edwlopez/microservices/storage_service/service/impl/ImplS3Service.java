package dev.edwlopez.microservices.storage_service.service.impl;

import dev.edwlopez.microservices.storage_service.repository.ProductImageRepository;
import dev.edwlopez.microservices.storage_service.service.aws.S3Service;
import dev.edwlopez.microservices.storage_service.types.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImplS3Service implements S3Service {
    @Autowired
    private S3Client s3Client;

    @Autowired
    private S3Presigner s3signer;

    @Value("${spring.destination.folder}")
    private String destinationFolder;

    private Map<String, Tuple<String, Instant>> keyUrlMap = new HashMap<>(10);

    @Override
    public String createBucket(String bucketName) {
        return s3Client.createBucket((b) -> {
            b.bucket(bucketName);
        }).location();
    }

    @Override
    public boolean checkIfBucketExist(String bucketName) {
        boolean flag = true;

        try {
            s3Client.headBucket((b) -> {
                b.bucket(bucketName);
            });
        } catch (S3Exception ex) {
            flag = false;
        }

        return flag;
    }

    @Override
    public List<String> getAllBuckets() {
        var buckets = s3Client.listBuckets();

        if (!buckets.hasBuckets()) return List.of();

        return buckets.buckets().stream().map(Bucket::name).toList();
    }

    @Override
    public boolean uploadFile(String bucketName, String key, Path fileLocation) {
        var putObjectReq = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        var response = s3Client.putObject(putObjectReq, fileLocation);

        return response.sdkHttpResponse().isSuccessful();
    }

    public boolean uploadFile (String bucketName, String key, byte[] file) {
        var putObjectReq = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        var response = s3Client.putObject(putObjectReq, RequestBody.fromBytes(file));

        return response.sdkHttpResponse().isSuccessful();
    }

    @Override
    public void downloadFile(String bucketName, String key) throws IOException {
        var getObjectReq = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        var bytesResponse = s3Client.getObjectAsBytes(getObjectReq);

        String filename = key;

        if (key.contains("/")) {
            filename = key.substring(key.lastIndexOf("/"));
        }

        Path path = Path.of(destinationFolder, filename);
        File file = path.toFile();

        file.getParentFile().mkdir();

        try (FileOutputStream writer = new FileOutputStream(file)) {
            writer.write(bytesResponse.asByteArray());
        } catch (IOException ex) {
            throw new IOException("Error al descargar el archivo", ex.getCause());
        }
    }

    @Override
    public String generatePresignedUploadURL(String bucketName, String key, Duration duration) {
        return "";
    }

    @Override
    public String generatePresignedDownloadURL(String bucketName, String key, Duration duration) {
        return "";
    }

    @Override
    public String getTemporalViewURLToResource(String bucketName, String key, Duration duration) throws IOException {
        return regenerateTemporalResourceURL(key, bucketName, duration);
    }

    private boolean isAvailableTemporalResourceURL (String key) {
        if (!this.keyUrlMap.containsKey(key)) return false;

        var url = this.keyUrlMap.get(key);
        var now = Instant.now();

        return !url.val2().isBefore(now);
    }

    private String regenerateTemporalResourceURL (String key, String bucketName, Duration duration) throws IOException {
        boolean flag = isAvailableTemporalResourceURL(key);

        if (flag) return this.keyUrlMap.get(key).val1();

        String url = generateTemporalResourceURL(key, bucketName, duration);
        Instant nowPlus = Instant.ofEpochMilli(Instant.now().toEpochMilli() + duration.toMillis());

         this.keyUrlMap.put(key, Tuple.of(url, nowPlus));

         return url;
    }

    private String generateTemporalResourceURL (String key, String bucketName, Duration duration) throws IOException {
        try (var presigner = s3signer) {
            var objReq = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            var presigReq = GetObjectPresignRequest.builder()
                    .signatureDuration(duration)
                    .getObjectRequest(objReq)
                    .build();

            var req = presigner.presignGetObject(presigReq);

            return req.url().toExternalForm();
        } catch (Exception ex) {
            throw new IOException("Error al intentar obtener una URL prefirmada para un objeto en S3", ex);
        }
    }
}
