package com.edu.planner.auth;

import com.edu.planner.dto.user.UserCredentials;
import com.edu.planner.dto.user.UserRequest;
import com.edu.planner.dto.user.UserResponse;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class AuthService {

    protected static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final BCryptPasswordEncoder passwordEncoder;

    private UserService userService;


    public AuthService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserResponse register(UserRequest userRequest) {
        if (userService.isUserExists(userRequest.getEmail())) {
            throw new RuntimeException("User already exists");
        }
        String hashedPassword = passwordEncoder.encode(userRequest.getPassword());

        return userService.createUser(userRequest);
    }


    public String login(UserCredentials userCredentials) {
        UserEntity user = userService.getUserByEmail(userCredentials.getEmail()).orElseThrow(() -> new RuntimeException("User does not exist: " + userCredentials.getEmail()));

        if (!passwordEncoder.matches(userCredentials.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }




}
