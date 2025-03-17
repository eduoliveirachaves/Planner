package com.edu.planner.controllers;

import com.edu.planner.exceptions.BadRequestException;
import com.edu.planner.exceptions.TaskNotFoundException;
import com.edu.planner.exceptions.UserNotFoundException;
import com.edu.planner.utils.Response;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;

/**
 * This class is responsible for handling exceptions that are thrown by the application.
 * It provides a way to handle exceptions globally and return a response to the client.
 */

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    // This method handles all other exceptions that are not handled by the other methods.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleGeneralException(Exception e) {
        logger.error("Exception : {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Response("An unexpected error occurred: " + e.getMessage()));
    }


    // This method handles UserNotFoundException exceptions.
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> handleUserNotFound(UserNotFoundException e) {
        logger.error("User not found: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Response(e.getMessage()));
    }


    // This method handles TaskNotFoundException exceptions.
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Response> handleTaskNotFound(TaskNotFoundException e) {
        logger.error("Task not found: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Response(e.getMessage()));
    }

    // This method handles NoResourceFoundException exceptions.
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Response> handleNoResourceFoundException(NoResourceFoundException e) {
        logger.error("No Resource found: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Response("This route doesn't exists: " + e.getMessage()));
    }

    // This method handles BadRequestException exceptions.
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response> handleBadRequestException(BadRequestException e) {
        logger.error("Bad Request: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Bad Request : " + e.getMessage(), Instant.now(), false));
    }
}

