package com.example.taskmanagement.dto;

import com.example.taskmanagement.enums.Priority;

import lombok.Builder;
import lombok.Data;

import java.time.*;

@Data
@Builder
public class TaskDTO {
    private Long taskId;

    private String taskName;
    private String description;

    private Priority priority;

    private LocalDate plannedCompletedDate;

    private Long courseId;

    private String courseName;
}
