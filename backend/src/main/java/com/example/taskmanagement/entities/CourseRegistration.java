package com.example.taskmanagement.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CourseRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long courseRegistrationId;

    private LocalDateTime registeredAt;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Course course;

}
