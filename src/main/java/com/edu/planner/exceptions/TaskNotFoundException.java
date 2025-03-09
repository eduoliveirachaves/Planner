package com.edu.planner.exceptions;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException() {
        super("TaskResponse not found");
    }
}
