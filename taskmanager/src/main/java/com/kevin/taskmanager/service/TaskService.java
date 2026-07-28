package com.kevin.taskmanager.service;

import com.kevin.taskmanager.dto.TaskRequestDTO;
import com.kevin.taskmanager.dto.TaskResponseDTO;
import com.kevin.taskmanager.exception.TaskAlreadyCompletedException;
import com.kevin.taskmanager.exception.TaskNotFoundException;
import com.kevin.taskmanager.model.Task;
import com.kevin.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<TaskResponseDTO> findAll(Boolean completed) {
        List<Task> tasks = (completed == null)
                ? repository.findAll()
                : repository.findByCompleted(completed);

        return tasks.stream()
                .map(TaskResponseDTO::new)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO findById(Long id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return new TaskResponseDTO(task);
    }

    public TaskResponseDTO create(TaskRequestDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());
        Task saved = repository.save(task);
        return new TaskResponseDTO(saved);
    }

    public TaskResponseDTO update(Long id, TaskRequestDTO dto) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());

        Task updated = repository.save(task);
        return new TaskResponseDTO(updated);
    }

    public TaskResponseDTO markAsCompleted(Long id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (task.isCompleted()) {
            throw new TaskAlreadyCompletedException(id);
        }

        task.setCompleted(true);
        task.setCompletedAt(LocalDateTime.now());

        Task updated = repository.save(task);
        return new TaskResponseDTO(updated);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        repository.deleteById(id);
    }
}