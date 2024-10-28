package com.example.taskmanagement.dto;

import java.util.UUID;
import com.example.taskmanagement.enums.Role;

import lombok.Data;

@Data
public class UserDTO {
    private UUID userId;

    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
