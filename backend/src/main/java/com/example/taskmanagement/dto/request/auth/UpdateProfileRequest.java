package com.example.taskmanagement.dto.request.auth;

import java.time.LocalDate;

import com.example.taskmanagement.enums.Gender;
import com.example.taskmanagement.enums.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateProfileRequest {

    
    @Schema(example = "Nguyen", required = false)
    private String firstName;

    @Schema(example = "Son Bao", required = false)
    private String lastName;

    @Schema(example = "USER", required = false)
    private Role role;

    @Schema(example = "MALE", required = false)
    private Gender gender;

    @Schema(example = "12-06-2001", required = false)
    private LocalDate birthday;

    @Schema(example = "https://firebasestorage.googleapis.com/v0/b/taskmanagement-d374b.appspot.com/o/082b7a33-26a6-4666-93bd-28aaf81be625.png?alt=media")
    private String avatarUrl;

}
