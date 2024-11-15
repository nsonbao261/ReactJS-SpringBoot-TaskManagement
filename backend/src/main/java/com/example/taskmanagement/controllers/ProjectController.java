package com.example.taskmanagement.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanagement.dto.request.projects.CreateProjectRequest;
import com.example.taskmanagement.dto.request.projects.UpdateProjectRequest;
import com.example.taskmanagement.dto.response.ApiResponse;
import com.example.taskmanagement.dto.response.ProjectDTO;
import com.example.taskmanagement.entities.Project;
import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.enums.Status;
import com.example.taskmanagement.services.ProjectService;
import com.example.taskmanagement.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Projects")
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public ProjectController(
            final ProjectService projectService,
            final UserService userService,
            final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ApiResponse> findProjectById(@PathVariable Long projectId) {
        try {

            final Project foundProject = projectService.findProjectById(projectId);

            // If project null, status bad request
            if (foundProject == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Project Not Found", null));
            }

            // if project exist, status ok, map to dto
            return ResponseEntity.ok()
                    .body(new ApiResponse("Project Found",
                            modelMapper.map(foundProject, ProjectDTO.class)));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Sever Error", null));
        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> findAllProject() {
        try {
            // Find All Projects
            List<Project> projects = projectService.findAllProject();

            // Map and return data
            return ResponseEntity.ok()
                    .body(new ApiResponse("Projects Found",
                            projects.stream().map(item -> modelMapper.map(item, ProjectDTO.class))));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Sever Error", null));
        }
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createProject(@RequestBody CreateProjectRequest request) {
        try {

            // Check if User Exist
            final User user = userService.findUserById(request.getUserId());

            if (user == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("User Not Found", null));
            }

            // Save Project
            final Project newProject = Project.builder()
                    .projectName(request.getProjectName())
                    .description(request.getDescription())
                    .plannedDueTime(request.getPlannedDueTime())
                    .user(user)
                    .build();

            final Project savedProject = projectService.save(newProject);
            // Map and return data
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Project Created",
                            modelMapper.map(savedProject, ProjectDTO.class)));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Sever Error", null));
        }
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ApiResponse> updateProject(
            @PathVariable Long projectId,
            @RequestBody UpdateProjectRequest request) {
        try {

            final Project foundProject = projectService.findProjectById(projectId);

            // If project null, status bad request
            if (foundProject == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Project Not Found", null));
            }

            // Update Project
            final Project updatedProject = projectService.updateExistingProject(foundProject, request);

            // if project exist, status ok, map to dto
            return ResponseEntity.ok()
                    .body(new ApiResponse("Project Found",
                            modelMapper.map(updatedProject, ProjectDTO.class)));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Sever Error", null));
        }
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<ApiResponse> deleteProject(
            @PathVariable Long projectId) {
        try {

            final Project foundProject = projectService.findProjectById(projectId);

            // If project null, status bad request
            if (foundProject == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Project Not Found", null));
            }

            // Delete Project and Return Data

            projectService.deleteProjectById(projectId);

            return ResponseEntity.ok()
                    .body(new ApiResponse("Project Deleted", null));
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Sever Error", null));
        }
    }

    @GetMapping("/find-by-user")
    public ResponseEntity<ApiResponse> findByUserId(@RequestParam String userId) {
        try {
            // Check if user exist

            final User user = userService.findUserById(userId);

            if (user == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("User Not Found", null));
            }

            // Find Project With User
            List<Project> projects = projectService.findPojectByUser(user);
            return ResponseEntity.ok()
                    .body(new ApiResponse(
                            "Projects found",
                            projects.stream().map(item -> modelMapper.map(item, ProjectDTO.class))));

        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Sever Error", null));
        }
    }

}
