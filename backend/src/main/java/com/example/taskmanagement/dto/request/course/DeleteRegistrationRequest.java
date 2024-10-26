package com.example.taskmanagement.dto.request.course;

import lombok.Data;

@Data
public class DeleteRegistrationRequest {
    private Long courseId;
    private String userId;
}
