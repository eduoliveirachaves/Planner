package com.edu.planner.controllers;

import com.edu.planner.annotations.CurrentUser;
import com.edu.planner.dto.task.TaskRequest;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.services.TaskService;
import com.edu.planner.utils.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class is responsible for handling requests related to tasks.
 * It provides endpoints for creating, updating, deleting, and retrieving tasks.
 */

@Slf4j
@Tag(name = "TaskResponse Controller", description = "Endpoints for managing tasks")
@RestController
@RequestMapping("/api/task")
public class TaskController {
    
    private final TaskService taskService;
    
    public TaskController (TaskService taskService) {
        this.taskService = taskService;
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Response> getTask (@PathVariable Long id, @CurrentUser UserEntity user) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new Response("TaskResponse Found", taskService.getTaskById(id, user)));
    }
    
    @GetMapping
    public ResponseEntity<Response> getUserTasks (@CurrentUser UserEntity user) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new Response("All tasks", taskService.getUserTasks(user)));
    }
    
    @GetMapping("/status")
    public ResponseEntity<Response> TasksByStatus (@RequestParam(name = "status") String status,
                                                   @CurrentUser UserEntity user) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new Response("Task with status " + status + " found",
                                                taskService.getTaskByStatus(user, status)));
    }
    
    @PostMapping
    public ResponseEntity<Response> createTask (@RequestBody TaskRequest task, @CurrentUser UserEntity user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(new Response("Task created", taskService.createTask(task, user)));
    }
    
    @PutMapping("{id}")
    public ResponseEntity<Response> updateTask (@PathVariable Long id, @RequestBody TaskRequest taskRequest,
                                                @CurrentUser UserEntity user) {
        /// here make sure to return the updated taskResponse
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new Response("Task updated", taskService.updateTask(id, taskRequest, user)));
    }
    
    @PatchMapping("{id}")
    public ResponseEntity<Response> updateFields (@PathVariable Long id, @RequestBody TaskRequest taskRequest,
                                                  @CurrentUser UserEntity user) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new Response("Task updated", taskService.updateTask(id, taskRequest, user)));
    }
    
    @PutMapping("{id}/status")
    public ResponseEntity<Response> updateTaskStatus (@PathVariable Long id, @CurrentUser UserEntity user) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new Response("Task updated", taskService.updateTaskStatus(id, user)));
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteTask (@PathVariable Long id, @CurrentUser UserEntity user) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new Response("Task deleted", taskService.deleteTask(id, user)));
    }
    
}
