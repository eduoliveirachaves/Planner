package com.edu.planner.mapper;

import com.edu.planner.dto.user.UserRequest;
import com.edu.planner.dto.user.UserResponse;
import com.edu.planner.entity.UserEntity;

public class UserMapper {
    public UserMapper() {
    }

    public static UserResponse toUserResponse(UserEntity user) {
        return new UserResponse(user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }


    public static UserEntity toUserEntity(UserRequest userRequest) {
        //caso tenha role, cria um novo usuário com role ( ADMIN ) se nao ROLE == USER
        return (userRequest.getRole() != null)
                ? new UserEntity(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword(), userRequest.getRole())
                : new UserEntity(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword());
    }

}
