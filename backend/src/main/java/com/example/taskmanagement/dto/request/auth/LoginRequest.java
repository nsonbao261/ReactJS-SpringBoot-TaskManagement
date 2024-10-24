package com.example.taskmanagement.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequest {

    @Schema(example = "test@example.com", required = true)
    private String email;

    @Schema(example = "12345678", required = true)
    private String password;
}
