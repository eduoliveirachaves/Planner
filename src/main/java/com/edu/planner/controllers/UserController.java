package com.edu.planner.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.edu.planner.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<String> user() {
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody String user, String firstName, String lastName, String email, String password) {
        return new ResponseEntity<>("User created", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> updateUser() {
        return new ResponseEntity<>("User updated", HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<String> deleteUser() {
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

}
