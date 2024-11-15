package com.example.taskmanagement.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.taskmanagement.dto.request.projects.UpdateProjectRequest;
import com.example.taskmanagement.entities.Project;
import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.repositories.ProjectRepository;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(final ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public Project findProjectById(final Long projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    public List<Project> findAllProject() {
        return projectRepository.findAll();
    }

    public List<Project> findPojectByUser(User user) {
        return projectRepository.findByUser(user);
    }

    public void deleteProjectById(final Long projectId) {
        projectRepository.deleteById(projectId);
    }

    public Project updateExistingProject(Project project, UpdateProjectRequest request) {
        if (request.getProjectName() != null)
            project.setProjectName(request.getProjectName());

        if (request.getDescription() != null)
            project.setDescription(request.getDescription());

        if (request.getPlannedDueTime() != null)
            project.setPlannedDueTime(request.getPlannedDueTime());

        if (request.getActualDueTime() != null)
            project.setActualDueTime(request.getActualDueTime());

        if (request.getStatus() != null)
            project.setStatus(request.getStatus());

        return this.save(project);
    }
}
