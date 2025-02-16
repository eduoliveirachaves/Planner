package com.edu.planner.controllers;

import com.edu.planner.dto.user.UserRequest;
import com.edu.planner.dto.user.UserResponse;
import com.edu.planner.services.UserService;
import com.edu.planner.utils.Response;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<Response> getUser(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("User found", userService.getUserById(id)));
    }


    //ok
    @GetMapping("/all")
    public ResponseEntity<Response> allUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(new Response("All " + users.size() + " Users Found", users));
    }


    //ok
    @PostMapping
    public ResponseEntity<Response> createUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response("User Created", userService.createUser(userRequest)));
    }


    //full update
    @PutMapping({"/{id}"})
    public ResponseEntity<Response> updateUser(@PathVariable long id, @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("User updated", userService.updateUser(id, userRequest)));
    }


    //for only updating the fields that are provided
    @PatchMapping("/{id}")
    public ResponseEntity<Response> patchUser(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("User Updated", userService.patchUser(id, updates)));
    }


    //ok
    @DeleteMapping
    public ResponseEntity<Response> deleteUser(@RequestParam long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("User Deleted", userService.deleteUser(id)));
    }

}
