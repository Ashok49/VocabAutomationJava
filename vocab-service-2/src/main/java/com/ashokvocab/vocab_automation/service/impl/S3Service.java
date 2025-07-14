package com.ashokvocab.vocab_automation.service.impl;

import com.ashokvocab.vocab_automation.exception.VocabAutomationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3Service {

    private static final Logger logger = LoggerFactory.getLogger(S3Service.class);
    private final S3Client s3Client;
    private final String pdfBucketName;
    private final String audioBucketName;

    public S3Service(@Value("${aws.s3.pdf-bucket-name}") String pdfBucketName,
                    @Value("${aws.s3.audio-bucket-name}") String audioBucketName,
                    @Value("${aws.access.key.id}") String accessKey,
                    @Value("${aws.secret.access.key}") String secretKey,
                    @Value("${aws.region}") String region) {

        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
        this.pdfBucketName = pdfBucketName;
        this.audioBucketName = audioBucketName;
    }

    public String uploadFile(byte[] content, String fileName, String contentType) {
        String bucketName = determineBucket(contentType);
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(request, RequestBody.fromBytes(content));

            return String.format("https://%s.s3.amazonaws.com/%s", bucketName, fileName);

        } catch (S3Exception e) {
            logger.error("Error uploading file to S3: {}", e.getMessage());
            throw new VocabAutomationException("Failed to upload file to S3", e);
        }
    }

    private String determineBucket(String contentType) {
        if (contentType.startsWith("application/pdf")) {
            return pdfBucketName;
        } else if (contentType.startsWith("audio/")) {
            return audioBucketName;
        }
        throw new VocabAutomationException("Unsupported content type: " + contentType);
    }

    public byte[] downloadFile(String fileUrlOrKey) {
        String bucketName;
        String key;

        // If fileUrl is a full S3 URL, extract bucket and key
        if (fileUrlOrKey.startsWith("https://")) {
            // Example: https://bucket-name.s3.amazonaws.com/key
            String[] parts = fileUrlOrKey.replace("https://", "").split("\\.s3\\.amazonaws\\.com/");
            bucketName = parts[0];
            key = parts[1];
        } else {
            // Assume key is provided, use pdfBucketName by default
            bucketName = pdfBucketName;
            key = fileUrlOrKey;
        }

        try {
            return s3Client.getObject(builder -> builder.bucket(bucketName).key(key)).readAllBytes();
        } catch (Exception e) {
            logger.error("Error downloading file from S3: {}", e.getMessage());
            throw new VocabAutomationException("Failed to download file from S3", e);
        }
    }
}