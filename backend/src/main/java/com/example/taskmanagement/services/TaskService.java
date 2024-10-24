package com.example.taskmanagement.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.taskmanagement.dto.request.task.UpdateTaskRequest;
import com.example.taskmanagement.entities.Task;
import com.example.taskmanagement.entities.User;
import com.example.taskmanagement.repositories.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(final TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public Task findTaskById(String taskId) {
        return taskRepository.findById(Long.parseLong(taskId))
                .orElse(null);
    }

    public List<Task> findAllTask() {
        return taskRepository.findAll();
    }

    public List<Task> findTaskByUser(User user) {
        return taskRepository.findByUser(user);
    }

    public Task updateExistingTask(Task task, UpdateTaskRequest request) {
        Task existingTask = task;
        existingTask.setTaskName(request.getTaskName());
        existingTask.setDescription(request.getDescription());
        existingTask.setPriority(request.getPriority());
        existingTask.setStatus(request.getStatus());
        existingTask.setActualStartDate(request.getActualStartDate());
        existingTask.setActualEndDate(request.getActualEndDate());
        existingTask.setPlannedStartDate(request.getActualEndDate());
        existingTask.setPlannedEndDate(request.getPlannedEndDate());
        return taskRepository.save(task);
    }

    public void deleteTaskById(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
