package com.edu.planner.auth;

import com.edu.planner.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final UserEntity user; // Store the full UserEntity

    public CustomUserDetails(UserEntity user) {
        this.user = user;
    }


    public UserEntity getUser() {
        return user; // Allows direct access to UserEntity
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // Assuming email is used for authentication
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

}
