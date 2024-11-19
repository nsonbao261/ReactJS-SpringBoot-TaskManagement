package com.example.taskmanagement.dto.request.projects;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateProjectRequest {

    @Schema(example = "Task Management System", required = true)
    private String projectName;

    @Schema(example = "This is sample provided description for Task Management System")
    private String description;

    @Schema(example = "2000-06-21", required = true)
    private LocalDateTime plannedDueTime;

    @Schema(example = "19ca8a0b-1116-4f7b-bb03-b8927907e799", required = true)
    private String userId;

}
