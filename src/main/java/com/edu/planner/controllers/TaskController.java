package com.edu.planner.controllers;

import com.edu.planner.annotations.CurrentUser;
import com.edu.planner.dto.task.Task;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.services.TaskService;
import com.edu.planner.utils.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class is responsible for handling requests related to tasks.
 * It provides endpoints for creating, updating, deleting, and retrieving tasks.
 */

@Tag(name = "Task Controller", description = "Endpoints for managing tasks")
@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;


    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping("{id}")
    public ResponseEntity<Response> getTask(@PathVariable Long id, @CurrentUser UserEntity user) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("Task Found", taskService.getTaskById(id, user)));
    }


    @GetMapping
    public ResponseEntity<Response> getUserTasks(@CurrentUser UserEntity user) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("All tasks", taskService.getUserTasks(user)));
    }


    @PostMapping
    public ResponseEntity<Response> createTask(@RequestBody Task task, @CurrentUser UserEntity user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Task created", taskService.createTask(task, user)));
    }


    @PutMapping("{id}")
    public ResponseEntity<Response> updateTask(@PathVariable Long id, @RequestBody Task task, @CurrentUser UserEntity user) {
        /// here make sure to return the updated task
        return ResponseEntity.status(HttpStatus.OK).body(new Response("Task updated", taskService.updateTask(id, task, user)));
    }

//    for admin to get all tasks - to be implemented
    /*
    @GetMapping("/all")
    public ResponseEntity<List<TaskEntity>> allTasks(@AuthenticationPrincipal UserEntity user) {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }*/


    @GetMapping("/status")
    public ResponseEntity<Response> TasksByStatus(@RequestParam(name = "status") String status, @CurrentUser UserEntity user) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("Task with status " + status + " found", taskService.getTaskByStatus(user, status)));
    }


    @PutMapping("{id}/status")
    public ResponseEntity<Response> updateTaskStatus(@PathVariable Long id, @CurrentUser UserEntity user) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("Task updated", taskService.updateTaskStatus(id, user)));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteTask(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("Task deleted", taskService.deleteTask(id)));
    }

}
