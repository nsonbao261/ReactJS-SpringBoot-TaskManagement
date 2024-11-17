package com.example.taskmanagement.dto.response;

import java.time.LocalDateTime;

import com.example.taskmanagement.enums.Priority;
import com.example.taskmanagement.enums.Status;

import lombok.Data;

@Data
public class TaskDTO {
    private Long taskId;

    private String taskName;
    private String description;

    private Status status;

    private Priority priority;

    private LocalDateTime plannedDueTime;

    private LocalDateTime actualDueTime;

    private Long projectId;
}
