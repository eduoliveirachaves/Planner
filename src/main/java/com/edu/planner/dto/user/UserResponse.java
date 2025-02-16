package com.edu.planner.dto.user;

import com.edu.planner.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.format.DateTimeFormatter;

/**
 * UserResponse class.
 * This class is used to create a user response.
 * It contains the user's first name, last name, email, createdAt and updatedAt.
 */
public class UserResponse {

    private final String firstName;
    private final String lastName;

    private final String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", locale = "pt-BR")
    private String createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", locale = "pt-BR")
    private String updatedAt;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public UserResponse(UserEntity userEntity) {
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.email = userEntity.getEmail();
        this.createdAt = userEntity.getCreatedAt().format(FORMATTER);
        this.updatedAt = userEntity.getUpdatedAt().format(FORMATTER);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
