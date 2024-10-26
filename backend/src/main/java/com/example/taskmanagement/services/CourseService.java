package com.example.taskmanagement.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.taskmanagement.dto.CourseRegistrationDTO;
import com.example.taskmanagement.dto.request.course.UpdateCourseRequest;
import com.example.taskmanagement.entities.Course;
import com.example.taskmanagement.entities.CourseRegistration;
import com.example.taskmanagement.entities.User;
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

    public Course findCourseById(Long courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

    public List<Course> findAllCourse() {
        return courseRepository.findAll();
    }

    public Course createCourse(Course course) {
        return this.courseRepository.save(course);
    }

    public void deleteCourseById(String courseId) {
        courseRepository.deleteById(Long.parseLong(courseId));
    }

    public Course updateExistingCourse(Course course, UpdateCourseRequest request) {
        var existingCourse = course;

        existingCourse.setCourseName(request.getCourseName());
        existingCourse.setDescription(request.getDescription());
        existingCourse.setMaxStudentNumber(request.getMaxStudentNumber());
        existingCourse.setUpdatedAt(LocalDateTime.now());

        return courseRepository.save(existingCourse);
    }

    public CourseRegistrationDTO mapCourseRegistrationDTO(CourseRegistration registration) {
        final Course course = registration.getCourse();

        final User user = registration.getStudent();

        return CourseRegistrationDTO.builder()
                .courseId(course.getCourseId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .registeredAt(registration.getRegisteredAt())
                .build();
    }

    public CourseRegistration saveRegistration(CourseRegistration registration) {
        return courseRegistrationRepository.save(registration);
    }

    public List<CourseRegistration> findCourseByUser(User user) {
        return courseRegistrationRepository.findByStudent(user);
    }

    public List<CourseRegistration> findCourseRegistrationByUserAndCourse(
            User user, Course course) {
        return courseRegistrationRepository.findByStudentAndCourse(user, course);
    }

    public void deleteCourseRegistration(Long registrationId) {
        courseRegistrationRepository.deleteById(registrationId);
    }
}
