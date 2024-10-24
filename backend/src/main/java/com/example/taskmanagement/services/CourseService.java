package com.example.taskmanagement.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.taskmanagement.dto.request.course.UpdateCourseRequest;
import com.example.taskmanagement.entities.Course;
import com.example.taskmanagement.repositories.CourseRegistrationRepository;
import com.example.taskmanagement.repositories.CourseRepository;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;

    public CourseService(
            final CourseRepository courseRepository,
            final CourseRegistrationRepository courseRegistrationRepository) {
        this.courseRegistrationRepository = courseRegistrationRepository;
        this.courseRepository = courseRepository;
    }

    public Course findCourseById(String courseId) {
        return courseRepository.findById(Long.parseLong(courseId)).orElse(null);
    }

    public List<Course> findAllCourse() {
        return courseRepository.findAll();
    }

    public Course createCourse(Course course) {
        return this.courseRepository.save(course);
    }

    public void deleteCourseById(String courseId) {
        this.courseRepository.deleteById(Long.parseLong(courseId));
    }

    public Course updateExistingCourse(Course course, UpdateCourseRequest request) {
        var existingCourse = course;

        existingCourse.setCourseName(request.getCourseName());
        existingCourse.setDescription(request.getDescription());
        existingCourse.setMaxStudentNumber(request.getMaxStudentNumber());
        existingCourse.setUpdatedAt(LocalDateTime.now());

        return courseRepository.save(existingCourse);
    }

}
