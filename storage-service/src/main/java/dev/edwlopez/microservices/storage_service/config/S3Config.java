package dev.edwlopez.microservices.storage_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Configuration
public class S3Config {
    @Value("${aws.access.key}")
    private String awsAccessKey;
    @Value("${aws.secret.key}")
    private String awsSecretKey;
    @Value("${aws.region}")
    private String region;

    @Bean
    public S3Client s3Client () {
        AwsCredentials credentials = AwsBasicCredentials.create(awsAccessKey, awsSecretKey);
        URI uri = URI.create("https://s3." + region + ".amazonaws.com");

        return S3Client.builder()
                .region(Region.of(this.region))
                .endpointOverride(uri)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
        .build();
    }

    @Bean
    public S3Presigner s3Presigner () {
        AwsCredentials credentials = AwsBasicCredentials.create(awsAccessKey, awsSecretKey);

        return S3Presigner.builder()
                .region(Region.of(this.region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
}
