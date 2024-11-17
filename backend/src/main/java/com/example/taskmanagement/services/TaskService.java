package com.example.taskmanagement.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.example.taskmanagement.dto.request.tasks.UpdateTaskRequest;
import com.example.taskmanagement.dto.response.Pagination;
import com.example.taskmanagement.dto.response.TaskDTO;
import com.example.taskmanagement.entities.Project;
import com.example.taskmanagement.entities.Task;
import com.example.taskmanagement.enums.Priority;
import com.example.taskmanagement.enums.Status;
import com.example.taskmanagement.repositories.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public TaskService(final TaskRepository taskRepository, final ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    public Task save(final Task task) {
        return taskRepository.save(task);
    }

    public Task findById(final Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    public Page<Task> findAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return taskRepository.findAll(pageable);
    }

    public void deleteById(final Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public Task updateTask(Task task, UpdateTaskRequest request) {
        if (request.getTaskName() != null) {
            task.setTaskName(request.getTaskName());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        if (request.getActualDueTime() != null) {
            task.setActualDueTime(request.getActualDueTime());
        }
        if (request.getPlannedDueTime() != null) {
            task.setPlannedDueTime(request.getPlannedDueTime());
        }

        return taskRepository.save(task);
    }

    public Page<Task> searchTask(
            Status status,
            Priority priority,
            String taskName,
            Project project,
            int pageNumber,
            int pageSize) {
        Task taskExample = Task.builder()
                .status(status)
                .priority(priority)
                .taskName(taskName)
                .project(project)
                .build();
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(StringMatcher.CONTAINING);
        Example<Task> example = Example.of(taskExample, exampleMatcher);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return taskRepository.findAll(example, pageable);
    }

    public TaskDTO convertToDTO(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }

    public Pagination<TaskDTO> convertToPaginationDTO(Page<TaskDTO> taskPages) {
        return new Pagination<TaskDTO>(
                taskPages.getContent(),
                taskPages.getNumber(),
                taskPages.getSize(),
                taskPages.getTotalElements(),
                taskPages.getTotalPages());
    }
}
