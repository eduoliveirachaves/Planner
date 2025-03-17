package com.edu.planner.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * UserResponse class.
 * This class is used to create a user response.
 * It contains the user's first name, last name, email, createdAt and updatedAt.
 * It is used in the UserController to return a user.
 * TO BE REFACTORED TO RECORD
 */


@Getter
@AllArgsConstructor
public class UserResponse {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final String name;

    private final String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", locale = "pt-BR")
    private String createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", locale = "pt-BR")
    private String updatedAt;


    public UserResponse(String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.email = email;
        this.createdAt = createdAt.format(FORMATTER);
        this.updatedAt = updatedAt.format(FORMATTER);
    }
    
    @Override
    public String toString() {
        return "UserResponse{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
