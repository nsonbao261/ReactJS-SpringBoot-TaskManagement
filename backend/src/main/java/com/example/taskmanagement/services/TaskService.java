package com.example.taskmanagement.services;

import org.springframework.stereotype.Service;

import com.example.taskmanagement.dto.TaskDTO;
import com.example.taskmanagement.dto.request.task.UpdateTaskRequest;
import com.example.taskmanagement.entities.Task;
import com.example.taskmanagement.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(final TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public Task findTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    public List<Task> findAllTask() {
        return taskRepository.findAll();
    }

    public Task updateTask(Task task, UpdateTaskRequest request) {
        Task existingTask = task;
        existingTask.setTaskName(request.getTaskName());
        existingTask.setDescription(request.description);
        existingTask.setPriority(request.getPriority());
        existingTask.setPlannedCompletedDate(request.getPlannedCompletedDate());
        existingTask.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(existingTask);
    }

    public void deleteTaskById(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public TaskDTO mapTaskToDTO(Task task) {
        return TaskDTO.builder()
                .courseId(task.getCourse().getCourseId())
                .courseName(task.getCourse().getCourseName())
                .taskId(task.getTaskId())
                .taskName(task.getTaskName())
                .description(task.getDescription())
                .priority(task.getPriority())
                .plannedCompletedDate(task.getPlannedCompletedDate())
                .build();
    }

}
