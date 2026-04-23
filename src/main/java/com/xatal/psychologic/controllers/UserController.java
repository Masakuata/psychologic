package com.xatal.psychologic.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xatal.psychologic.entities.User;
import com.xatal.psychologic.services.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUser() {
        return ResponseEntity.ok("Hello, User!");
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        userService.createUser(user.getUsername(), user.getEmail());
        return ResponseEntity.ok("User created!");
    }
}
