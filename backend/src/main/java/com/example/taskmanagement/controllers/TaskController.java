package com.example.taskmanagement.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanagement.dto.request.tasks.CreateTaskRequest;
import com.example.taskmanagement.dto.request.tasks.UpdateTaskRequest;
import com.example.taskmanagement.dto.response.ApiResponse;
import com.example.taskmanagement.dto.response.Pagination;
import com.example.taskmanagement.dto.response.TaskDTO;
import com.example.taskmanagement.entities.Project;
import com.example.taskmanagement.entities.Task;
import com.example.taskmanagement.enums.Priority;
import com.example.taskmanagement.enums.Status;
import com.example.taskmanagement.exception.CustomException;
import com.example.taskmanagement.services.ProjectService;
import com.example.taskmanagement.services.TaskService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Tasks")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    private final ProjectService projectService;

    public TaskController(
            final TaskService taskService,
            final ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createTask(@RequestBody CreateTaskRequest request) {

        // Check If Project Exist

        final Project project = projectService.findProjectById(request.getProjectId());

        if (project == null) {
            throw new CustomException("Project Not Found");
        }
        // Save Task

        Task task = Task.builder()
                .taskName(request.getTaskName())
                .description(request.getDescription())
                .plannedDueTime(request.getPlannedDueTime())
                .priority(request.getPriority())
                .project(project)
                .build();

        Task savedTask = taskService.save(task);

        // Map and Return DTO

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(
                        "Task Created",
                        taskService.convertToDTO(savedTask)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAllTasks(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "12") int pageSize) {

        Page<TaskDTO> tasks = taskService.findAll(pageNumber, pageSize)
                .map(object -> taskService.convertToDTO(object));

        Pagination<TaskDTO> taskPage = taskService.convertToPaginationDTO(tasks);
        return ResponseEntity.ok()
                .body(new ApiResponse(
                        "Tasks Found",
                        taskPage));

    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse> findTaskById(@PathVariable Long taskId) {

        final Task task = taskService.findById(taskId);

        if (task == null) {
            throw new CustomException("Task Not Found");
        }

        return ResponseEntity.ok()
                .body(new ApiResponse("Task Found", taskService.convertToDTO(task)));

    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResponse> deleteTaskById(@PathVariable Long taskId) {
        final Task task = taskService.findById(taskId);

        if (task == null) {
            throw new CustomException("Task Not Found");
        }

        taskService.deleteById(taskId);

        return ResponseEntity.ok()
                .body(new ApiResponse("Task Deleted", null));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ApiResponse> updateTask(@PathVariable Long taskId, @RequestBody UpdateTaskRequest request) {
        final Task task = taskService.findById(taskId);

        if (task == null) {
            throw new CustomException("Task Not Found");
        }

        final Task updatedTask = taskService.updateTask(task, request);

        return ResponseEntity.ok()
                .body(new ApiResponse("Task Updated", taskService.convertToDTO(updatedTask)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchTask(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) String taskName,
            @RequestParam(required = false) Long projectId,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "12") int pageSize) {

        Project project = null;
        if (projectId != null) {
            // Find Project
            project = projectService.findProjectById(projectId);
            if (project == null) {
                throw new CustomException("Project Not Found");
            }
        }
        final Page<Task> tasks = taskService.searchTask(
                status,
                priority,
                taskName,
                project,
                pageNumber,
                pageSize);

        final Page<TaskDTO> taskDTOs = tasks.map(object -> taskService.convertToDTO(object));

        return ResponseEntity.ok().body(new ApiResponse(
                "Tasks Found",
                taskService.convertToPaginationDTO(taskDTOs)));
    }
}
