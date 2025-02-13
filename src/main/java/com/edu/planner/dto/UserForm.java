package com.edu.planner.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserForm {

    @NotBlank
    private String firstName;
    private String lastName;
    @Email
    @NotBlank
    private String email;
    private String password;

    public UserForm() {
    }

    public UserForm(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

}
