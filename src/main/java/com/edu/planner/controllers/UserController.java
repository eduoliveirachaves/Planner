package com.edu.planner.controllers;

import com.edu.planner.annotations.CurrentUser;
import com.edu.planner.dto.user.UserRequest;
import com.edu.planner.dto.user.UserResponse;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.services.UserService;
import com.edu.planner.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * This class is responsible for handling requests related to users.
 * It provides endpoints for creating, updating, deleting, and retrieving users.
 */

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Controller", description = "Endpoints for managing users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    //return the current user - full details
    @GetMapping("/me")
    public ResponseEntity<Response> me(@CurrentUser UserEntity user) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("User found", user));
    }


    @Operation(summary = "Get user profile", description = "Retrieves the profile of the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Found",
                    content = @Content(mediaType = "applicaiton/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/profile")
    public ResponseEntity<Response> getProfile(@CurrentUser UserEntity user) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("User info", userService.getProfile(user)));
    }


    //ok
    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody UserRequest userRequest) {
        System.out.println(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response("User Created", userService.createUser(userRequest)));
    }


    //update full user
    //need a security check
    @PutMapping
    public ResponseEntity<Response> updateUser(@CurrentUser UserEntity user, @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("User updated", userService.updateUser(user, userRequest)));
    }


    //for only updating the fields that are provided
    //need a securirty ckeck
    @PatchMapping
    public ResponseEntity<Response> patchUser(
            @CurrentUser UserEntity user,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("User Updated", userService.patchUser(user, updates)));
    }


    //auto delete
    @DeleteMapping
    public ResponseEntity<Response> deleteUser(@CurrentUser UserEntity user) {
        return ResponseEntity.status(HttpStatus.OK).body(new Response("User Deleted", userService.deleteUser(user)));
    }





}
