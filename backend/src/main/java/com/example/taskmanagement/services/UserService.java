package com.example.taskmanagement.services;

import org.springframework.stereotype.Service;

import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.repositories.UserRepository;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId)).orElse(null);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
