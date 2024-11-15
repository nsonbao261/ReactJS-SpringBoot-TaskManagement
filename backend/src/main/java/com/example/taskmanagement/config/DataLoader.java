package com.example.taskmanagement.config;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.enums.Gender;
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

        private void seedUserData() {
                userRepository.save(User.builder()
                                .firstName("Nguyen")
                                .lastName("Son Bao")
                                .email("user@example.com")
                                .password(passwordEncoder.encode("12345678"))
                                .createdAt(LocalDateTime.now())
                                .role(Role.USER)
                                .gender(Gender.MALE)
                                .birthday(LocalDate.parse("2001-06-12"))
                                .build());

                userRepository.save(User.builder()
                                .firstName("Nguyen")
                                .lastName("Son Bao")
                                .email("manager@example.com")
                                .password(passwordEncoder.encode("12345678"))
                                .createdAt(LocalDateTime.now())
                                .role(Role.MANAGER)
                                .gender(Gender.MALE)
                                .birthday(LocalDate.parse("2001-06-12"))
                                .build());

                userRepository.save(User.builder()
                                .firstName("Nguyen")
                                .lastName("Son Bao")
                                .email("admin@example.com")
                                .password(passwordEncoder.encode("12345678"))
                                .createdAt(LocalDateTime.now())
                                .role(Role.ADMIN)
                                .gender(Gender.FEMALE)
                                .birthday(LocalDate.parse("2001-06-12"))
                                .build());
        }

        @Override
        public void run(String... args) {

                // User
                if (userRepository.findAll().isEmpty())
                        seedUserData();
        }

}
