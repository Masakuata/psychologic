package com.xatal.psychologic.Structures;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public record Login(String email, String password) {
    public Login {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or empty required values on payload");
        }
    }
}
