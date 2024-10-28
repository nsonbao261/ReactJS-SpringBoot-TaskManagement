package com.example.taskmanagement.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanagement.dto.request.task.CreateTaskRequest;
import com.example.taskmanagement.dto.request.task.UpdateTaskRequest;
import com.example.taskmanagement.dto.response.ApiResponse;
import com.example.taskmanagement.entities.Course;
import com.example.taskmanagement.entities.Task;
import com.example.taskmanagement.services.CourseService;
import com.example.taskmanagement.services.TaskService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Task")
@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;
    private final CourseService courseService;

    public TaskController(final TaskService taskService, final CourseService courseService) {
        this.taskService = taskService;
        this.courseService = courseService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createTask(@RequestBody CreateTaskRequest request) {
        try {

            final Course course = courseService.findCourseById(request.getCourseId());

            if (course == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Course Not Found", null));
            }

            Task newTask = Task.builder()
                    .taskName(request.taskName)
                    .description(request.description)
                    .priority(request.priority)
                    .plannedCompletedDate(request.plannedCompletedDate)
                    .createdAt(LocalDateTime.now())
                    .build();

            final Task savedTask = taskService.saveTask(newTask);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(
                            "Task Created",
                            taskService.mapTaskToDTO(savedTask)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse> findTaskById(@PathVariable String taskId) {
        try {
            final Task task = taskService.findTaskById(Long.parseLong(taskId));

            if (task == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Task Not Found", null));
            }

            return ResponseEntity.ok()
                    .body(new ApiResponse(
                            "Task Found",
                            taskService.mapTaskToDTO(task)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> findAllTask() {
        try {
            final List<Task> tasks = taskService.findAllTask();

            return ResponseEntity.ok()
                    .body(new ApiResponse(
                            "Task Found",
                            tasks.stream().map(
                                    task -> taskService.mapTaskToDTO(task))));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ApiResponse> updateTask(
            @PathVariable String taskId,
            @RequestBody UpdateTaskRequest request) {

        try {
            final Task task = taskService.findTaskById(Long.parseLong(taskId));

            if (task == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Task Not Found", null));
            }

            final Task updatedTask = taskService.updateTask(task, request);

            return ResponseEntity.ok()
                    .body(new ApiResponse(
                            "Task Updated",
                            taskService.mapTaskToDTO(updatedTask)));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }

    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable String taskId) {
        try {
            final Task task = taskService.findTaskById(Long.parseLong(taskId));

            if (task == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Task Not Found", null));
            }

            taskService.deleteTaskById(null);

            return ResponseEntity.ok()
                    .body(new ApiResponse("Task Deleted", null));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }
}
