package com.xatal.psychologic.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xatal.psychologic.services.StorageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/storage")
public class StorageController {
    private final StorageService storageService;

    @PostMapping("/create-bucket/{bucketname}")
    public ResponseEntity<?> createBucket(@PathVariable String bucketname) {
        storageService.createBucket(bucketname);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{bucketname}/{objectkey}")
    public ResponseEntity<String> getUploadUrl(@PathVariable String bucketname, @PathVariable String objectkey) {
        String uploadUrl = storageService.getUploadUrl(bucketname, objectkey);
        return new ResponseEntity<>(uploadUrl, HttpStatus.OK);
    }
}
