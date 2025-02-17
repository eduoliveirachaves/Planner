package com.edu.planner.services;

import com.edu.planner.auth.JwtService;
import com.edu.planner.dto.user.UserCredentials;
import com.edu.planner.entity.UserEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;

    private final JwtService jwtService;


    public AuthService(@Lazy UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }


    public String login(UserCredentials userCredentials) {
        UserEntity user = userService.getUserByEmail(userCredentials.getEmail()).orElseThrow(() -> new RuntimeException("User does not exist: " + userCredentials.getEmail()));

        if (!jwtService.matches(userCredentials.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtService.createToken(user.getId());
    }
}
