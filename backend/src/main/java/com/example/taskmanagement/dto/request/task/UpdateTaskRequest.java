package com.example.taskmanagement.dto.request.task;

import com.example.taskmanagement.enums.Priority;

import java.time.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateTaskRequest {
    @Schema(example = "Updated Task", required = true)
    public String taskName;

    @Schema(example = "Updated Example Description")
    public String description;

    @Schema(example = "LOW", required = true)
    public Priority priority;

    @Schema(example = "01-01-2025", required = true)
    public LocalDate plannedCompletedDate;
}
