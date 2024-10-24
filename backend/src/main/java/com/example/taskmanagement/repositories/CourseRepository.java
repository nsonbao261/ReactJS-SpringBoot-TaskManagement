package com.example.taskmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanagement.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
