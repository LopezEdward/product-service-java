package dev.edwlopez.microservices.storage_service.service.impl;

import dev.edwlopez.microservices.storage_service.repository.ProductImageRepository;
import dev.edwlopez.microservices.storage_service.service.aws.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;

@Service
public class ImplS3Service implements S3Service {
    @Autowired
    private S3Client s3Client;

    @Value("${spring.destination.folder}")
    private String destinationFolder;

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
}
