package com.example.taskmanagement.dto.request.tasks;

import java.time.LocalDateTime;

import com.example.taskmanagement.enums.Priority;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateTaskRequest {

    @Schema(example = "Example Task 1", required = true)
    private String taskName;

    @Schema(example = "Example Description for Task 1")
    private String description;

    @Schema(example = "MEDIUM", required = true)
    private Priority priority;

    @Schema(example = "2024-10-21", required = true)
    private LocalDateTime plannedDueTime;

    @Schema(example = "1", type = "integer", required = true)
    private Long projectId;
}
