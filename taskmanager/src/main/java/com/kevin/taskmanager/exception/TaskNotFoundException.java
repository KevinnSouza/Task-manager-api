package com.kevin.taskmanager.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Tarefa com id " + id + " não encontrada.");
    }
}