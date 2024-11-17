package com.example.taskmanagement.dto.request.tasks;

import java.time.LocalDateTime;

import com.example.taskmanagement.enums.Priority;
import com.example.taskmanagement.enums.Status;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateTaskRequest {
    @Schema(example = "Updated Example Task")
    private String taskName;

    @Schema(example = "Updated Example Description")
    private String description;

    @Schema(example = "MEDIUM")
    private Priority priority;

    @Schema(example = "COMPLETED")
    private Status status;

    @Schema(example = "2024-10-21")
    private LocalDateTime plannedDueTime;

    @Schema(example = "2024-11-21")
    private LocalDateTime actualDueTime;

}
