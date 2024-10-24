package com.example.taskmanagement.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanagement.dto.CourseDTO;
import com.example.taskmanagement.dto.request.course.CreateCourseRequest;
import com.example.taskmanagement.dto.request.course.UpdateCourseRequest;
import com.example.taskmanagement.dto.response.ApiResponse;
import com.example.taskmanagement.entities.Course;
import com.example.taskmanagement.services.CourseService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Tag(name = "Course")
@RestController
@RequestMapping("/api/course")
public class CourseController {

    private final ModelMapper modelMapper;
    private final CourseService courseService;

    public CourseController(final CourseService courseService, final ModelMapper modelMapper) {
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createCourse(@RequestBody CreateCourseRequest request) {
        try {

            Course newCourse = Course.builder()
                    .courseName(request.getCourseName())
                    .description(request.getDescription())
                    .createdAt(LocalDateTime.now())
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(
                            "Course Created",
                            modelMapper.map(newCourse, CourseDTO.class)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<ApiResponse> findCourseById(@PathVariable String courseId) {
        try {
            final Course foundCourse = courseService.findCourseById(courseId);

            if (foundCourse == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Course Not Found", null));
            }
            ;

            return ResponseEntity.ok()
                    .body(new ApiResponse(
                            "Internal Server Error",
                            modelMapper.map(foundCourse, CourseDTO.class)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> findAllCourse() {
        try {
            final List<Course> courses = courseService.findAllCourse();

            return ResponseEntity.ok()
                    .body(new ApiResponse(
                            "Courses Found",
                            courses.stream().map(
                                    course -> modelMapper.map(course, CourseDTO.class)).toList()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<ApiResponse> updateCourse(
            @PathVariable String courseId,
            @RequestBody UpdateCourseRequest request) {
        try {
            final Course foundCourse = courseService.findCourseById(courseId);

            if (foundCourse == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Course Not Found", null));
            }

            final Course updatedCourse = courseService.updateExistingCourse(foundCourse, request);

            return ResponseEntity.ok()
                    .body(new ApiResponse(
                            "Course Updated",
                            modelMapper.map(updatedCourse, CourseDTO.class)));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }
}
