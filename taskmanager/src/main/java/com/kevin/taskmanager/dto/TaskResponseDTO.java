package com.kevin.taskmanager.dto;

import com.kevin.taskmanager.model.Priority;
import com.kevin.taskmanager.model.Task;

import java.time.LocalDateTime;

public class TaskResponseDTO {

    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    public TaskResponseDTO(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.priority = task.getPriority();
        this.completed = task.isCompleted();
        this.createdAt = task.getCreatedAt();
        this.completedAt = task.getCompletedAt();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Priority getPriority() { return priority; }
    public boolean isCompleted() { return completed; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }
}