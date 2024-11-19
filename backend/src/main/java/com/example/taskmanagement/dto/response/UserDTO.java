package com.example.taskmanagement.dto.response;

import java.time.LocalDate;
import java.util.UUID;
import com.example.taskmanagement.enums.Gender;
import com.example.taskmanagement.enums.Role;
import lombok.Data;

@Data
public class UserDTO {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String avatarUrl;
    private Role role;
    private Gender gender;
    private LocalDate birthday;
}
