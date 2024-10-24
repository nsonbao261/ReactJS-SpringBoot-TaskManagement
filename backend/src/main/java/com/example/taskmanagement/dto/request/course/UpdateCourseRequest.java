package com.example.taskmanagement.dto.request.course;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateCourseRequest {
    @Schema(example = "Updated Course 1", required = false)
    private String courseName;

    @Schema(example = "Example Description", required = false)
    private String description;

    @Schema(example = "100", required = false, type = "interger")
    private int maxStudentNumber;

}
