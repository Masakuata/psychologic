package com.xatal.psychologic.configuration;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@RequiredArgsConstructor
@Configuration
public class S3Config {
    private final SupabaseConfig supabaseConfig;

    @Bean
    public S3Client s3Client() {
        return S3Client
                .builder()
                .region(supabaseConfig.getS3Region())
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(supabaseConfig.getS3Id(), supabaseConfig.getS3Key())))
                .endpointOverride(URI.create(supabaseConfig.getS3Url()))
                .forcePathStyle(true)
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        S3Configuration s3Configuration = S3Configuration
                .builder()
                .pathStyleAccessEnabled(true)
                .build();
        return S3Presigner
                .builder()
                .region(supabaseConfig.getS3Region())
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(supabaseConfig.getS3Id(), supabaseConfig.getS3Key())))
                .endpointOverride(URI.create(supabaseConfig.getS3Url()))
                .serviceConfiguration(s3Configuration)
                .build();
    }
}
