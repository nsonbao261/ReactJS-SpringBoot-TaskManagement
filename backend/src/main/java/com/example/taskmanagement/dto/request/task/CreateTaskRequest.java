package com.example.taskmanagement.dto.request.task;

import com.example.taskmanagement.enums.Priority;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreateTaskRequest {

    @Schema(example = "First Task", required = true)
    public String taskName;

    @Schema(example = "Example Description")
    public String description;

    @Schema(example = "HIGH", required = true)
    public Priority priority;

    @Schema(example = "01-01-2025", required = true)
    public LocalDate plannedCompletedDate;

    @Schema(type = "integer", example = "1", required = true)
    public Long courseId;
}
