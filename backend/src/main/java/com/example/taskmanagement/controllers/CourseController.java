package com.example.taskmanagement.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanagement.dto.CourseDTO;
import com.example.taskmanagement.dto.request.course.CreateCourseRequest;
import com.example.taskmanagement.dto.request.course.DeleteRegistrationRequest;
import com.example.taskmanagement.dto.request.course.RegisterCourseRequest;
import com.example.taskmanagement.dto.request.course.UpdateCourseRequest;
import com.example.taskmanagement.dto.response.ApiResponse;
import com.example.taskmanagement.entities.Course;
import com.example.taskmanagement.entities.CourseRegistration;
import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.services.CourseService;
import com.example.taskmanagement.services.UserService;

import io.swagger.v3.oas.annotations.Parameter;
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
    private final UserService userService;

    public CourseController(
            final CourseService courseService,
            final ModelMapper modelMapper,
            final UserService userService) {
        this.courseService = courseService;
        this.modelMapper = modelMapper;
        this.userService = userService;
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
            final Course foundCourse = courseService.findCourseById(Long.parseLong(courseId));

            if (foundCourse == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Course Not Found", null));
            }

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
            final Course foundCourse = courseService.findCourseById(Long.parseLong(courseId));

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

    @DeleteMapping("/{courseId}")
    public ResponseEntity<ApiResponse> deleteCourse(@PathVariable String courseId) {
        try {
            final Course foundCourse = courseService.findCourseById(Long.parseLong(courseId));

            if (foundCourse == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Course Not Found", null));
            }

            courseService.deleteCourseById(courseId);

            return ResponseEntity.ok()
                    .body(new ApiResponse("Course Deleted", null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }

    @PostMapping("/register-course")
    public ResponseEntity<ApiResponse> registerCourse(@RequestBody RegisterCourseRequest request) {
        try {

            // Check if Course exist
            final Course course = courseService.findCourseById(request.getCourseId());

            if (course == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Course Not Found", null));
            }

            // Check if User exist
            final User user = userService.findUserById(request.getUserId());

            if (user == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Course Not Found", null));
            }

            // Create and Save Course Register

            final CourseRegistration registration = CourseRegistration.builder()
                    .course(course)
                    .student(user)
                    .registeredAt(LocalDateTime.now())
                    .build();

            final CourseRegistration savedRegistration = courseService.saveRegistration(registration);

            // Map and return data
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(
                            "Course Registerd",
                            courseService.mapCourseRegistrationDTO(savedRegistration)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }

    }

    @GetMapping("/find-by-user/")
    public ResponseEntity<ApiResponse> findCourseByUserId(
            @Parameter(name = "userId", example = "d14b0d07-2b94-449c-bd2d-96efa454cf94", required = true) @RequestParam String userId) {

        try {

            // Check if user exist
            final User user = userService.findUserById(userId);

            if (user == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Course Not Found", null));
            }

            // Find courses by userId

            List<CourseRegistration> foundCourses = courseService.findCourseByUser(user);

            // Map and return data
            return ResponseEntity.ok()
                    .body(new ApiResponse(
                            "Courses Found",
                            foundCourses.stream().map(
                                    registration -> courseService.mapCourseRegistrationDTO(registration)).toList()));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }

    public ResponseEntity<ApiResponse> deleteRegistration(@RequestBody DeleteRegistrationRequest request) {
        try {
            final String userId = request.getUserId();
            final Long courseId = request.getCourseId();
            // Check if user exist

            final User user = userService.findUserById(userId);

            if (user == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Course Not Found", null));
            }
            // Check if course exist

            final Course course = courseService.findCourseById(request.getCourseId());

            if (course == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Course Not Found", null));
            }

            // Check if registration exist by User and Course
            final List<CourseRegistration> registrations = courseService.findCourseRegistrationByUserAndCourse(user,
                    course);

            // Delete registration

            registrations.forEach(
                    registration -> courseService.deleteCourseRegistration(registration.getCourseRegistrationId()));

            return ResponseEntity.ok()
                    .body(new ApiResponse(
                            "Registration Deleted",
                            null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }
}
