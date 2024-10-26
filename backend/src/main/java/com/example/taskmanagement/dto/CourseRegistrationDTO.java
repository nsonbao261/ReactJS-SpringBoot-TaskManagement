package com.example.taskmanagement.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseRegistrationDTO {
    private UUID userId;

    private String firstName;
    private String lastName;
    private String email;

    private Long courseId;

    private String courseName;
    private String description;
    private LocalDateTime registeredAt;
}
