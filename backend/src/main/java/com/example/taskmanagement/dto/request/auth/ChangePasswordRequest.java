package com.example.taskmanagement.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ChangePasswordRequest {

    @Schema(example = "UID-UID-UID", required = true)
    private String userId;

    @Schema(example = "12345678", required = true)
    private String currentPassword;

    @Schema(example = "0123456789", required = true)
    private String newPassword;
}
