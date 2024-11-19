package com.example.taskmanagement.controllers;

import org.springframework.data.domain.Page;
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
import com.example.taskmanagement.dto.response.Pagination;
import com.example.taskmanagement.dto.response.ProjectDTO;
import com.example.taskmanagement.entities.Project;
import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.enums.Status;
import com.example.taskmanagement.exception.CustomException;
import com.example.taskmanagement.services.ProjectService;
import com.example.taskmanagement.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Projects")
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

        private final ProjectService projectService;
        private final UserService userService;

        public ProjectController(
                        final ProjectService projectService,
                        final UserService userService) {
                this.userService = userService;
                this.projectService = projectService;
        }

        @GetMapping("/{projectId}")
        public ResponseEntity<ApiResponse> findProjectById(@PathVariable Long projectId) {

                final Project foundProject = projectService.findProjectById(projectId);

                // If project null, status bad request
                if (foundProject == null) {
                        return ResponseEntity.badRequest()
                                        .body(new ApiResponse("Project Not Found", null));
                }

                // if project exist, status ok, map to dto
                return ResponseEntity.ok()
                                .body(new ApiResponse("Project Found",
                                                projectService.convertToDTO(foundProject)));
        }

        @GetMapping()
        public ResponseEntity<ApiResponse> findAllProject(
                        @RequestParam(defaultValue = "0") int pageNumber,
                        @RequestParam(defaultValue = "12") int pageSize) {

                // Find All Projects
                Page<Project> projects = projectService.findAllProject(pageNumber, pageSize);

                Page<ProjectDTO> projectDTOs = projects.map(
                                item -> projectService.convertToDTO(item));

                // Map and return data
                return ResponseEntity.ok()
                                .body(new ApiResponse("Projects Found",
                                                projectService.convertToPaginationDTO(projectDTOs)));
        }

        @PostMapping()
        public ResponseEntity<ApiResponse> createProject(@RequestBody CreateProjectRequest request) {

                // Check if User Exist
                final User user = userService.findUserById(request.getUserId());

                if (user == null) {
                        throw new CustomException("User Not Found");
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
                                                projectService.convertToDTO(savedProject)));
        }

        @PutMapping("/{projectId}")
        public ResponseEntity<ApiResponse> updateProject(
                        @PathVariable Long projectId,
                        @RequestBody UpdateProjectRequest request) {

                final Project foundProject = projectService.findProjectById(projectId);

                // If project null, status bad request
                if (foundProject == null) {
                        throw new CustomException("Project Not Found");
                }

                // Update Project
                final Project updatedProject = projectService.updateExistingProject(foundProject, request);

                // if project exist, status ok, map to dto
                return ResponseEntity.ok()
                                .body(new ApiResponse("Project Found",
                                                projectService.convertToDTO(updatedProject)));
        }

        @DeleteMapping("/{projectId}")
        public ResponseEntity<ApiResponse> deleteProject(
                        @PathVariable Long projectId) {

                final Project foundProject = projectService.findProjectById(projectId);

                // If project null, status bad request
                if (foundProject == null) {
                        throw new CustomException("Project Not Found");
                }

                // Delete Project and Return Data

                projectService.deleteProjectById(projectId);

                return ResponseEntity.ok()
                                .body(new ApiResponse("Project Deleted", null));
        }

        @GetMapping("/search")
        public ResponseEntity<ApiResponse> searchProject(
                        @RequestParam(required = false) String projectName,
                        @RequestParam(required = false) Status status,
                        @RequestParam(required = false) String userId,
                        @RequestParam(defaultValue = "0") int pageNumber,
                        @RequestParam(defaultValue = "12") int pageSize) {

                // Check if User exist
                User user = null;

                if (userId != null) {
                        user = userService.findUserById(userId);
                        if (user == null) {
                                throw new CustomException("User Not Found");
                        }
                }

                // Find Project

                final Page<Project> projects = projectService.searchProject(projectName, status, user, pageNumber,
                                pageSize);

                // Map and return data

                Page<ProjectDTO> projectDTOs = projects.map(item -> projectService.convertToDTO(item));

                return ResponseEntity.ok()
                                .body(new ApiResponse(
                                                "Project Found",
                                                projectService.convertToPaginationDTO(projectDTOs)));

        }

}
