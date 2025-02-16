package com.edu.planner.controllers;

import com.edu.planner.exceptions.TaskNotFoundException;
import com.edu.planner.exceptions.UserNotFoundException;
import com.edu.planner.utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Response(e.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleGeneralException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Response("An unexpected error occurred: " + e.getMessage()));
    }


    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Response> handleTaskNotFound(TaskNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Response(e.getMessage()));
    }
}

