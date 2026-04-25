package com.xatal.psychologic.repositories;

import java.net.URI;
import java.time.Duration;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.xatal.psychologic.configuration.SupabaseConfig;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@RequiredArgsConstructor
@Component
public class StorageRepository {
    private final SupabaseConfig supabaseConfig;
    private S3Client s3Client;
    private S3Presigner s3Presigner;
    private final Region region = Region.US_EAST_2;

    private final Duration URL_DURATION = Duration.ofMinutes(15);

    @PostConstruct
    public void init() {
        AwsCredentials credentials = AwsBasicCredentials.create(supabaseConfig.getS3Id(), supabaseConfig.getS3Key());
        s3Client = S3Client
                .builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .endpointOverride(URI.create(supabaseConfig.getS3Url()))
                .forcePathStyle(true)
                .build();

        S3Configuration s3Configuration = S3Configuration
                .builder()
                .pathStyleAccessEnabled(true)
                .build();
        s3Presigner = S3Presigner
                .builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .endpointOverride(URI.create(supabaseConfig.getS3Url()))
                .serviceConfiguration(s3Configuration)
                .build();
    }

    public boolean createBucket(String bucketName) {
        String uniqueBucketName = generateRandomBucketSuffix(bucketName);
        CreateBucketResponse bucketResponse = s3Client.createBucket(builder -> builder.bucket(uniqueBucketName));
        return bucketResponse.sdkHttpResponse().isSuccessful();
    }

    public boolean deleteBucket(String bucketName) {
        Optional<Bucket> optionalBucket = findBucketByName(bucketName);
        if (optionalBucket.isPresent()) {
            Bucket bucket = optionalBucket.get();
            emptyBucket(bucket);
            s3Client.deleteBucket(builder -> builder.bucket(bucket.name()));
            return true;
        }
        return false;
    }

    public void emptyBucket(Bucket bucket) {
        s3Client.listObjectsV2Paginator(builder -> builder.bucket(bucket.name()))
                .stream()
                .flatMap(response -> response.contents().stream())
                .forEach(s3Object -> s3Client
                        .deleteObject(builder -> builder.bucket(bucket.name()).key(s3Object.key())));
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

    private String generateRandomBucketSuffix(String bucketName) {
        String randomSuffix = java.util.UUID.randomUUID().toString()
                .substring(0, 8).replace("-", "");
        return bucketName + "-" + randomSuffix;
    }
}
