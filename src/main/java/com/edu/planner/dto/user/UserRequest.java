package com.edu.planner.dto.user;

import com.edu.planner.utils.Enums.Gender;
import com.edu.planner.utils.Enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * UserRequest class.
 * This class is used to create a user request.
 * It contains the user's first name, last name, email and password.
 * It is used in the UserController to create or build (update) a user.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequest {

    private String name;

    @Email
    private String email;

    @Size(min = 5, max = 30)
    private String password;

    private Role role;

    private String username;

    private Integer age;

    private Integer height;

    private Integer weight;

    private Gender gender;


    public UserRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }


    public UserRequest(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight;
    }
}
