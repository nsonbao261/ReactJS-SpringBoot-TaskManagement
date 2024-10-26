package com.example.taskmanagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanagement.entities.Course;
import com.example.taskmanagement.entities.CourseRegistration;
import com.example.taskmanagement.entities.User;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {

    public List<CourseRegistration> findByStudent(User student);

    public List<CourseRegistration> findByStudentAndCourse(User student, Course course);

}
