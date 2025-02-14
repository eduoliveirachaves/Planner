package com.edu.planner.controllers;

import com.edu.planner.entity.TaskEntity;
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
    public ResponseEntity<TaskEntity> task(@PathVariable String id) {
        long numericalId;
        try {
            numericalId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        TaskEntity t = taskService.getTaskById(numericalId);
        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody TaskEntity taskEntity) {
        taskService.createTask(taskEntity);
        return new ResponseEntity<>("TaskEntity created", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskEntity>> allTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<TaskEntity>> TasksByStatus(@RequestParam(name = "status", required = true) String status) {

        return new ResponseEntity<>(taskService.getTaskByStatus(status), HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<TaskEntity> deleteTask(@RequestParam(name = "id", required = true) long id) {
        return new ResponseEntity<>(taskService.deleteTask(id), HttpStatus.OK);
    }

}
