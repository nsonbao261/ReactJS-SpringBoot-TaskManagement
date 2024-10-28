package com.example.taskmanagement.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.taskmanagement.entities.Course;
import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.enums.Role;
import com.example.taskmanagement.repositories.CourseRepository;
import com.example.taskmanagement.repositories.UserRepository;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(
            final UserRepository userRepository,
            final CourseRepository courseRepository,
            final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
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

    private void seedCourseData() {

        courseRepository.save(Course.builder()
                .courseName("Information Technology")
                .description("This is Course Description")
                .maxStudentNumber(120)
                .createdAt(LocalDateTime.now())
                .build());

        courseRepository.save(Course.builder()
                .courseName("Artificial Inteligence")
                .description("This is Course Description")
                .maxStudentNumber(120)
                .createdAt(LocalDateTime.now())
                .build());

        courseRepository.save(Course.builder()
                .courseName("Computer Science")
                .description("This is Course Description")
                .maxStudentNumber(100)
                .createdAt(LocalDateTime.now())
                .build());

        courseRepository.save(Course.builder()
                .courseName("System Security")
                .description("This is Course Description")
                .maxStudentNumber(100)
                .createdAt(LocalDateTime.now())
                .build());

        courseRepository.save(Course.builder()
                .courseName("Computer Software Developer")
                .description("This is Course Description")
                .maxStudentNumber(100)
                .createdAt(LocalDateTime.now())
                .build());
    }

    @Override
    public void run(String... args) {

        // User
        if (userRepository.findAll().isEmpty())
            seedUserData();

        // Course
        if (courseRepository.findAll().isEmpty())
            seedCourseData();
    }

}
