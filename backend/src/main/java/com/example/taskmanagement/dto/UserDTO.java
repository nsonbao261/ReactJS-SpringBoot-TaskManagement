package com.example.taskmanagement.dto;

import java.util.UUID;
import java.util.Set;
import com.example.taskmanagement.enums.Role;

import lombok.Data;
import com.example.taskmanagement.entities.Task;

@Data
public class UserDTO {
    private UUID userId;

    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private Set<Task> tasks;
}
