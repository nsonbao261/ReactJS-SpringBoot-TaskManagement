package com.example.taskmanagement.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.enums.Role;
import com.example.taskmanagement.repositories.UserRepository;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(
            final UserRepository userRepository,
            final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void seedUserData() {
        userRepository.save(User.builder()
                .firstName("Nguyen")
                .lastName("Son Bao")
                .email("user@example.com")
                .password(passwordEncoder.encode("12345678"))
                .createdAt(LocalDateTime.now())
                .role(Role.USER)
                .build());

        userRepository.save(User.builder()
                .firstName("Nguyen")
                .lastName("Son Bao")
                .email("manager@example.com")
                .password(passwordEncoder.encode("12345678"))
                .createdAt(LocalDateTime.now())
                .role(Role.MANAGER)
                .build());

        userRepository.save(User.builder()
                .firstName("Nguyen")
                .lastName("Son Bao")
                .email("admin@example.com")
                .password(passwordEncoder.encode("12345678"))
                .createdAt(LocalDateTime.now())
                .role(Role.ADMIN)
                .build());
    }

    @Override
    public void run(String... args) {

        // User
        seedUserData();
    }

}
