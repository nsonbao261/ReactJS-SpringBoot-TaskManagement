package com.example.taskmanagement.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanagement.dto.TaskDTO;
import com.example.taskmanagement.entities.Task;
import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.enums.Status;
import com.example.taskmanagement.request.task.CreateTaskRequest;
import com.example.taskmanagement.request.task.UpdateTaskRequest;
import com.example.taskmanagement.response.ApiResponse;
import com.example.taskmanagement.services.TaskService;
import com.example.taskmanagement.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public TaskController(
            final TaskService taskService,
            final ModelMapper modelMapper,
            final UserService userService) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Tag(name = "Task")
    @PostMapping()
    public ResponseEntity<ApiResponse> createTask(@RequestBody CreateTaskRequest request) {
        try {

            final User user = userService.findUserById(request.getUserId());

            if (user == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("User Not Found", null));
            }
            final Task newTask = Task.builder()
                    .taskName(request.getTaskName())
                    .description(request.getDescription())
                    .plannedStartDate(request.getPlannedStartDate())
                    .plannedEndDate(request.getPlannedEndDate())
                    .priority(request.getPriority())
                    .createdAt(LocalDateTime.now())
                    .status(Status.PENDING)
                    .user(user)
                    .build();

            final Task createdTask = taskService.save(newTask);
            final TaskDTO createdTaskDto = modelMapper.map(createdTask, TaskDTO.class);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Task Created", createdTaskDto));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }

    @Tag(name = "Task")
    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse> findTaskById(@PathVariable String taskId) {
        try {

            final Task foundTask = taskService.findTaskById(taskId);
            if (foundTask == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Task Not Found", null));
            }

            return ResponseEntity.ok()
                    .body(new ApiResponse(
                            "Task Found",
                            modelMapper.map(foundTask, TaskDTO.class)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }

    @Tag(name = "Task")
    @GetMapping()
    public ResponseEntity<ApiResponse> findAllTask() {
        try {

            final List<Task> foundTasks = taskService.findAllTask();
            final List<TaskDTO> foundTaskDTOs = foundTasks.stream().map(
                    task -> modelMapper.map(task, TaskDTO.class)).toList();
            return ResponseEntity.ok()
                    .body(new ApiResponse(
                            "All tasks found",
                            foundTaskDTOs));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }

    @Tag(name = "Task")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable String taskId) {
        try {

            final Task foundTask = taskService.findTaskById(taskId);
            if (foundTask == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Task Not Found", null));
            }

            taskService.deleteTaskById(foundTask.getTaskId());

            return ResponseEntity.ok()
                    .body(new ApiResponse(
                            "Task Deleted",
                            null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }

    @Tag(name = "Task")
    @PutMapping("/{taskId}")
    public ResponseEntity<ApiResponse> updateTask(@PathVariable String taskId, @RequestBody UpdateTaskRequest request) {
        try {

            final Task foundTask = taskService.findTaskById(taskId);
            if (foundTask == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Task Not Found", null));
            }

            return ResponseEntity.ok()
                    .body(new ApiResponse(
                            "Task Updated",
                            modelMapper.map(taskService.updateExistingTask(foundTask, request), TaskDTO.class)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }

    @Tag(name = "Task")
    @GetMapping("/find-by-user/{userId}")
    public ResponseEntity<ApiResponse> findTaskByUserId(@PathVariable String userId) {
        try {
            final User user = userService.findUserById(userId);

            if (user == null) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("User Not Found", null));
            }

            final List<Task> foundTasks = taskService.findTaskByUser(user);
            final List<TaskDTO> foundTaskDTOs = foundTasks.stream().map(
                    task -> modelMapper.map(task, TaskDTO.class)).toList();
            return ResponseEntity.ok()
                    .body(new ApiResponse(
                            "All tasks found",
                            foundTaskDTOs));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse("Internal Server Error", null));
        }
    }
}
