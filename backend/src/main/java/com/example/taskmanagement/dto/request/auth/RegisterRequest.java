package com.example.taskmanagement.dto.request.auth;


import com.example.taskmanagement.enums.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RegisterRequest {

    @Schema(example = "Nguyen", required = false)
    private String firstName;


    @Schema(example = "Son Bao", required = false)
    private String lastName;

    @Schema(example = "test@example.com", required = true)
    private String email;

    @Schema(example = "12345678", required = true)
    private String password;

    @Schema(example = "USER", required = true)
    private Role role;
}
