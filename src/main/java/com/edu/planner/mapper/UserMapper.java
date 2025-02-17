package com.edu.planner.mapper;

import com.edu.planner.dto.user.UserRequest;
import com.edu.planner.dto.user.UserResponse;
import com.edu.planner.entity.UserEntity;

public class UserMapper {
    public UserMapper() {
    }

    public static UserResponse toUserResponse(UserEntity user) {
        return new UserResponse(user.getFirstName(), user.getLastName(), user.getEmail(), String.valueOf(user.getRole()));
    }

    public static UserEntity toUserEntity(UserRequest userRequest) {
        return new UserEntity(userRequest.getFirstName(), userRequest.getLastName(), userRequest.getEmail(), userRequest.getPassword(), userRequest.getRole());
    }
}
