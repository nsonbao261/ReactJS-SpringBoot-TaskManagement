package com.example.taskmanagement.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.taskmanagement.dto.request.projects.UpdateProjectRequest;
import com.example.taskmanagement.dto.response.Pagination;
import com.example.taskmanagement.dto.response.ProjectDTO;
import com.example.taskmanagement.entities.Project;
import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.repositories.ProjectRepository;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    public ProjectService(
            final ProjectRepository projectRepository,
            final ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public Project findProjectById(final Long projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    public Page<Project> findAllProject(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return projectRepository.findAll(pageable);
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

    public ProjectDTO convertToDTO(Project project) {
        ProjectDTO projectDTO = modelMapper.map(project, ProjectDTO.class);
        return projectDTO;
    }

    public Pagination<ProjectDTO> convertToPaginationDTO(Page<ProjectDTO> projectPages) {
        return new Pagination<ProjectDTO>(
                projectPages.getContent(),
                projectPages.getNumber(),
                projectPages.getSize(),
                projectPages.getTotalElements(),
                projectPages.getTotalPages());
    }
}
