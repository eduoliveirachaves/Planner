package com.edu.planner.controllers;

import com.edu.planner.annotations.CurrentUser;
import com.edu.planner.dto.user.UserRequest;
import com.edu.planner.dto.user.UserResponse;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.services.UserService;
import com.edu.planner.utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for handling requests related to users.
 * It provides endpoints for creating, updating, deleting, and retrieving users.
 */

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/me")
    public ResponseEntity<Response> me(@CurrentUser UserEntity user) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("User found", user));
    }


    //ok
    @GetMapping("/admin/{id}")
    public ResponseEntity<Response> getUser(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("User found", userService.getUserById(id)));
    }


    //ok
    @GetMapping("/admin/all")
    public ResponseEntity<Response> allUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(new Response("All " + users.size() + " Users Found", users));
    }


    //ok
    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody UserRequest userRequest) {
        System.out.println(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response("User Created", userService.createUser(userRequest)));
    }


    //full update
    @PutMapping
    public ResponseEntity<Response> updateUser(@CurrentUser UserEntity user, @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("User updated", userService.updateUser(user, userRequest)));
    }


    //for only updating the fields that are provided
    @PatchMapping
    public ResponseEntity<Response> patchUser(
            @CurrentUser UserEntity user,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("User Updated", userService.patchUser(user, updates)));
    }


    //ok
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("User Deleted", userService.deleteUser(id)));
    }


    //ok
    @DeleteMapping
    public ResponseEntity<Response> deleteUser(@CurrentUser UserEntity user) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("User Deleted", userService.deleteUser(user)));
    }


    @GetMapping("/admin/debug-auth")
    public ResponseEntity<?> debugAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Is Authenticated? " + authentication.isAuthenticated());
        System.out.println("Principal: " + authentication.getPrincipal());
        System.out.println("Authorities: " + authentication.getAuthorities());

        return ResponseEntity.ok(authentication);
    }


}
