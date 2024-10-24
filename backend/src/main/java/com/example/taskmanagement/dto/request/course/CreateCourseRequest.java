package com.example.taskmanagement.dto.request.course;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateCourseRequest {

    @Schema(example = "Example Course 1", required = true)
    private String courseName;

    @Schema(example = "Example Description", required = false)
    private String description;

    @Schema(example = "100", required = true, type = "interger")
    private int maxStudentNumber;

}
