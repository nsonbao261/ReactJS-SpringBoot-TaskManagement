package com.example.taskmanagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanagement.entities.Project;
import com.example.taskmanagement.entities.User;

public interface ProjectRepository extends JpaRepository<Project, Long>{

    public List<Project> findByUser(User user);

}
