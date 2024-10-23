package com.example.taskmanagement.request.task;

import com.example.taskmanagement.enums.Priority;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreateTaskRequest {

    @Schema(example = "Example Task 1", required = true)
    private String taskName;

    @Schema(example = "Example Description", required = false)
    private String description;

    @Schema(example = "LOW", required = true)
    private Priority priority;

    @Schema(example = "2024-01-01", required = true)
    private LocalDate plannedStartDate;

    @Schema(example = "2024-12-12", required = true)
    private LocalDate plannedEndDate;

    @Schema(example = "XXX-XXX-XXX", required = true)
    private String userId;
}
