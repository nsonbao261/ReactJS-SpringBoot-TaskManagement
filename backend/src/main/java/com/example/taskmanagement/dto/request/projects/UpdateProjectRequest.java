package com.example.taskmanagement.dto.request.projects;

import java.time.LocalDateTime;

import com.example.taskmanagement.enums.Status;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateProjectRequest {
    @Schema(example = "Food Delivery System")
    private String projectName;

    @Schema(example = "This is sample provided description for Task Management System")
    private String description;

    @Schema(example = "2000-06-21")
    private LocalDateTime plannedDueTime;

    @Schema(example = "2024-12-14")
    private LocalDateTime actualDueTime;

    @Schema(example = "CANCELED")
    private Status status;
}
