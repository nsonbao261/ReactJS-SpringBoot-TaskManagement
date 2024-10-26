package com.example.taskmanagement.dto;

import com.example.taskmanagement.enums.Priority;
import com.example.taskmanagement.enums.Status;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDTO {
    private Long taskId;
    private String taskName;
    private String description;
    private Priority priority;
    private Status status;
    private LocalDate plannedStartDate;
    private LocalDate plannedEndDate;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;
}
