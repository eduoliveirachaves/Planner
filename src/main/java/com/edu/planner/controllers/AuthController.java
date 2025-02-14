package com.edu.planner.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/register")
    public String registerUser() {
        return "UserEntity registered";
    }

    @GetMapping("/logout")
    public String logout() {
        return "Logout";
    }



}
