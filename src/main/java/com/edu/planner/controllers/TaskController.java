package com.edu.planner.controllers;

import com.edu.planner.models.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.edu.planner.services.TaskService;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Task> task(@PathVariable String id) {
        long numericalId;
        try {
            numericalId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Task t = taskService.getTaskById(numericalId);
        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody Task task) {
        taskService.createTask(task);
        return new ResponseEntity<>("Task created", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> allTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Task>> TasksByStatus(@RequestParam(name = "status", required = true) String status) {

        return new ResponseEntity<>(taskService.getTaskByStatus(status), HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<Task> deleteTask(@RequestParam(name = "id", required = true) long id) {
        return new ResponseEntity<>(taskService.deleteTask(id), HttpStatus.OK);
    }

}
