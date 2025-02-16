package com.edu.planner.auth;

import com.edu.planner.dto.user.UserCredentials;
import com.edu.planner.utils.Response;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<Response> login(@Valid @RequestBody UserCredentials userCredentials) {
        return ResponseEntity.status(200).body(new Response("User authenticated", Collections.singletonMap("token", authService.login(userCredentials))));
    }

}
