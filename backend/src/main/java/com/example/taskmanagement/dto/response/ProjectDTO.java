package com.example.taskmanagement.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.taskmanagement.enums.Status;

import lombok.Data;

@Data
public class ProjectDTO {
    private Long projectId;

    private String projectName;
    private String description;
    private LocalDateTime plannedDueTime;
    private Status status;
    private UUID userId;
}
