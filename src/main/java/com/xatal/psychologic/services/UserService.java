package com.xatal.psychologic.services;

import org.springframework.stereotype.Service;

import com.xatal.psychologic.entities.User;
import com.xatal.psychologic.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void createUser(String username, String email) {
        userRepository.save(new User(username, email));
    }
}
