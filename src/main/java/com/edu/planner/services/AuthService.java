package com.edu.planner.services;

import com.edu.planner.auth.JwtService;
import com.edu.planner.dto.user.UserCredentials;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.utils.CustomCookie;
import jakarta.servlet.http.Cookie;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * AuthService class.
 * This class is used to authenticate users.
 * It provides methods to login and logout users.
 * It uses the UserService and JwtService classes.
 * Used by the AuthController.
 */

@Service
public class AuthService {

    private final UserService userService;

    private final JwtService jwtService;


    public AuthService(@Lazy UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }


    public Cookie login(UserCredentials userCredentials) {
        UserEntity user = userService.getUserByEmail(userCredentials.getEmail()).orElseThrow(() -> new RuntimeException("User does not exist: " + userCredentials.getEmail()));

        if (!jwtService.matches(userCredentials.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.createToken(user.getId());

        return CustomCookie.create(token);
    }

    public Cookie logout() {
        return CustomCookie.delete();
    }
}
