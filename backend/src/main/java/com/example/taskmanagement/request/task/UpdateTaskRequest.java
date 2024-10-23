package com.example.taskmanagement.request.task;

import java.time.LocalDate;

import com.example.taskmanagement.enums.Priority;
import com.example.taskmanagement.enums.Status;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateTaskRequest {
    @Schema(example = "Example Updated Task", required = false)
    private String taskName;

    @Schema(example = "Example Updated Description", required = false)
    private String description;

    @Schema(example = "HIGH", required = false)
    private Priority priority;

    @Schema(example = "PROCESSING", required = false)
    private Status status;

    @Schema(example = "2024-01-01", required = false)
    private LocalDate plannedStartDate;

    @Schema(example = "2024-12-12", required = false)
    private LocalDate plannedEndDate;

    @Schema(example = "2024-02-02", required = false)
    private LocalDate actualStartDate;

    @Schema(example = "2024-08-08", required = false)
    private LocalDate actualEndDate;

}
