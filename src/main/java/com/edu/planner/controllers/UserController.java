package com.edu.planner.controllers;

import com.edu.planner.entity.UserEntity;
import com.edu.planner.models.UserRequest;
import com.edu.planner.utils.Response;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.edu.planner.services.UserService;

import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //just a test
    @GetMapping
    public ResponseEntity<String> user() {
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

    //ok
    @GetMapping("{id}")
    public ResponseEntity<Response> user(@PathVariable long id) {
        return userService.getUserById(id);
    }

    //ok
    @GetMapping("/all")
    public ResponseEntity<Response> allUsers() {
        return userService.getAllUsers();
    }

    //ok
    @PostMapping
    public ResponseEntity<Response> createUser(@Valid @RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    //working on it
    @PutMapping({"/{id}"})
    public ResponseEntity<Response> updateUser( @PathVariable long id, @RequestBody UserRequest userRequest) {
        return userService.updateUser(id, userRequest);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response> patchUser(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        return userService.patchUser(id, updates);
    }

    //0
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam long id) {
        return new ResponseEntity<>("UserEntity deleted", HttpStatus.OK);
    }

}
