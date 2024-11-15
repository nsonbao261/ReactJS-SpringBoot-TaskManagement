package com.example.taskmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.enums.Role;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);
}
