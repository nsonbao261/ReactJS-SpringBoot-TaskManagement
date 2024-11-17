package com.example.taskmanagement.services;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.example.taskmanagement.dto.request.auth.UpdateProfileRequest;
import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.enums.Gender;
import com.example.taskmanagement.enums.Role;
import com.example.taskmanagement.repositories.UserRepository;

import java.time.LocalDate;
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

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public User updateExistingUser(User user, UpdateProfileRequest request) {

        try {
            final String firstName = request.getFirstName() != null ? request.getFirstName() : user.getFirstName();
            final String lastName = request.getLastName() != null ? request.getLastName() : user.getLastName();
            final String avatarUrl = request.getAvatarUrl() != null ? request.getAvatarUrl() : user.getAvatarUrl();
            final Gender gender = request.getGender() != null ? request.getGender() : user.getGender();
            final LocalDate birthday = request.getBirthday() != null ? request.getBirthday() : user.getBirthday();
            final Role role = request.getRole() != null ? request.getRole() : user.getRole();

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAvatarUrl(avatarUrl);
            user.setGender(gender);
            user.setRole(role);
            user.setBirthday(birthday);
            return userRepository.save(user);
        } catch (Exception e) {
            return null;
        }

    }

    public List<User> findUserByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public List<User> findUserByRoleAndGender(Role role, Gender gender) {
        User userExample = new User();
        userExample.setGender(gender);
        userExample.setRole(role);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<User> example = Example.of(userExample, matcher);

        return userRepository.findAll(example);
    }


    
}
