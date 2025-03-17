package com.edu.planner.controllers;

import com.edu.planner.services.TaskService;
import com.edu.planner.services.UserService;
import com.edu.planner.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * This class is for all routes that admin can acess
 * Methods: getAllUsers, getUser, deleteUser, allTasks, getUserTasks, getTask, debugAuth.
 */


@RestController()
@RequestMapping("/api/admin")
public class AdminController {

    private UserService userService;

    private TaskService taskService;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping("/users")
    public ResponseEntity<Response> getAllUsers() {
        return ResponseEntity.ok(new Response("All users", userService.getAllUsers()));
    }


    // path variable doesnt make sense or make?
    @GetMapping("/user/{id}")
    public ResponseEntity<Response> getUser(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("User found", userService.getUserById(id)));
    }


    @GetMapping("/debug-auth")
    public ResponseEntity<?> debugAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Is Authenticated? " + authentication.isAuthenticated());
        System.out.println("Principal: " + authentication.getPrincipal());
        System.out.println("Authorities: " + authentication.getAuthorities());

        return ResponseEntity.ok(authentication);
    }


    //path variable doesnt make sense or make?
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("User Deleted", userService.deleteUser(id)));
    }


    @GetMapping("/tasks")
    public ResponseEntity<Response> allTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("All tasks", taskService.getAllTasks()));
    }

    @GetMapping("/tasks/{userID}")
    public ResponseEntity<Response> getUserTasks(@PathVariable long userID) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("All tasks", taskService.getUserTasks(userID)));
    }


    public ResponseEntity<Response> getTask(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("Task found", taskService.getTaskById(id)));
    }



}
