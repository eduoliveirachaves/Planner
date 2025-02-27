package com.edu.planner.controllers;

import com.edu.planner.exceptions.TaskNotFoundException;
import com.edu.planner.exceptions.UserNotFoundException;
import com.edu.planner.utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for handling exceptions that are thrown by the application.
 * It provides a way to handle exceptions globally and return a response to the client.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Response(e.getMessage()));
    }


    // This method handles all other exceptions that are not handled by the other methods.
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

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoResourceFoundException(NoResourceFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "THIS ROUTE DOESN'T EXIST, YOUR STUPID");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}

