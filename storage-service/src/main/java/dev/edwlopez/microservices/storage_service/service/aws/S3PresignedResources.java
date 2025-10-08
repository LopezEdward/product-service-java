package dev.edwlopez.microservices.storage_service.service.aws;

import java.io.IOException;
import java.time.Duration;

public interface S3PresignedResources {
    String getTemporalViewURLToResource(String bucketName, String key, Duration duration) throws IOException;
}
