package com.example.taskmanagement.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.repositories.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(final UserRepository userRepository) {
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
