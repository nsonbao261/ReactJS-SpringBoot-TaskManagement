package com.example.taskmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanagement.entities.CourseRegistration;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {

}
