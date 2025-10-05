package dev.edwlopez.microservices.storage_service.service.aws;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;

public interface S3Service {
    String createBucket(String bucketName);
    boolean checkIfBucketExist (String bucketName);
    List<String> getAllBuckets();
    boolean uploadFile(String bucketName, String key, Path fileLocation);
    void downloadFile(String bucketName, String key) throws IOException;
    String generatePresignedUploadURL (String bucketName, String key, Duration duration);
    String generatePresignedDownloadURL (String bucketName, String key, Duration duration);
}
