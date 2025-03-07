package com.edu.planner.controllers;

import com.edu.planner.dto.user.UserCredentials;
import com.edu.planner.services.AuthService;
import com.edu.planner.utils.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
    * AuthController class
    * RestController that handles authentication requests
    * Used to login, logout and allow check if the user is authenticated
*/

@Tag(name = "Auth Controller", description = "Endpoints for managing authentication")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/check")
    public ResponseEntity<Response> check() {
        return ResponseEntity.status(200).body(new Response("User authenticated", null, true));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@Valid @RequestBody UserCredentials userCredentials, HttpServletResponse response) {
        response.addCookie(authService.login(userCredentials));
        return ResponseEntity.status(200).body(new Response("User authenticated", null, true));
    }

    @PostMapping("/logout")
    public ResponseEntity<Response> logout(HttpServletResponse response) {
        response.addCookie(authService.logout());
        return ResponseEntity.status(200).body(new Response("User logged out", null, true));
    }





}
