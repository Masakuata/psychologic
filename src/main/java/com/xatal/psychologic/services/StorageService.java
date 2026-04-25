package com.xatal.psychologic.services;

import org.springframework.stereotype.Service;

import com.xatal.psychologic.repositories.StorageRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StorageService {
    private final StorageRepository storageRepository;

    public void createBucket(String bucketName) {
        storageRepository.createBucket(bucketName);
    }

    public String getUploadUrl(String bucketName, String objectKey) {
        return storageRepository.getUploadUrl(bucketName, objectKey);
    }

    public void deleteBucket(String bucketname) {
        storageRepository.deleteBucket(bucketname);
    }
}
