package com.kevin.taskmanager.exception;

public class TaskAlreadyCompletedException extends RuntimeException {
    public TaskAlreadyCompletedException(Long id) {
        super("A tarefa " + id + " já está marcada como concluída.");
    }
}