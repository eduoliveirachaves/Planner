package com.edu.planner.controllers;

import com.edu.planner.annotations.CurrentUser;
import com.edu.planner.entity.TaskEntity;
import com.edu.planner.dto.task.Task;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<Response> getTask(@PathVariable Long id, @AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("Task Found", taskService.getTaskById(id)));
    }

    @PostMapping
    public ResponseEntity<Response> createTask(@RequestBody Task task, @CurrentUser UserEntity user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication);
        System.out.println("Principal: " + authentication.getPrincipal());

        return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Task created", taskService.createTask(task, user)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskEntity>> allTasks(@AuthenticationPrincipal UserEntity user) {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<TaskEntity>> TasksByStatus(@RequestParam(name = "status", required = true) String status, @AuthenticationPrincipal UserEntity user) {
        return new ResponseEntity<>(taskService.getTaskByStatus(TaskEntity.Status.valueOf(status)), HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<TaskEntity> deleteTask(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.deleteTask(id), HttpStatus.OK);
    }

}
