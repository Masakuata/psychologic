package com.xatal.psychologic.repositories;

import java.time.Duration;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@RequiredArgsConstructor
@Component
public class StorageRepository {
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    private final Duration URL_DURATION = Duration.ofMinutes(15);

    public boolean createBucket(String bucketName) {
        CreateBucketResponse bucketResponse = s3Client.createBucket(builder -> builder.bucket(bucketName));
        return bucketResponse.sdkHttpResponse().isSuccessful();
    }

    public void deleteBucket(String bucketName) {
        emptyBucket(bucketName);
        s3Client.deleteBucket(builder -> builder.bucket(bucketName));
    }

    public void emptyBucket(String bucketName) {
        s3Client.listObjectsV2Paginator(builder -> builder.bucket(bucketName))
                .stream()
                .flatMap(response -> response.contents().stream())
                .forEach(s3Object -> s3Client
                        .deleteObject(builder -> builder.bucket(bucketName).key(s3Object.key())));
    }

    public String getUploadUrl(String bucketName, String objectKey) {
        Optional<Bucket> optionalBucket = findBucketByName(bucketName);
        if (optionalBucket.isEmpty()) {
            throw new RuntimeException("Bucket not found: " + bucketName);
        }

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(optionalBucket.get().name())
                .key(objectKey)
                .build();
        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(putObjectRequest)
                .signatureDuration(URL_DURATION)
                .build();
        return s3Presigner.presignPutObject(presignRequest).url().toString();
    }

    private Optional<Bucket> findBucketByName(String bucketName) {
        return s3Client.listBuckets().buckets()
                .stream()
                .filter(bucket -> bucket.name().contains(bucketName))
                .findFirst();
    }
}
