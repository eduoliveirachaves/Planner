package com.edu.planner.services;

import com.edu.planner.entity.UserEntity;
import com.edu.planner.exceptions.UserNotFoundException;
import com.edu.planner.models.UserRequest;
import com.edu.planner.models.UserResponse;
import com.edu.planner.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserResponse createUser(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity(userRequest.getFirstName(), userRequest.getLastName(), userRequest.getEmail(), userRequest.getPassword());

        if (isUserExists(userRequest.getEmail())) {
            throw new RuntimeException("User already exists");
        }

        return new UserResponse(userRepository.save(userEntity));
    }


    public List<UserResponse> getAllUsers() {
        if (userRepository.findAll().isEmpty()) {
            throw new RuntimeException("No users found");
        }

        return userRepository.findAll()
                .stream()
                .map(UserResponse::new).
                toList();
    }


    public UserResponse getUserById(long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        return new UserResponse(user);
    }


    public UserResponse updateUser(long id, UserRequest userRequest) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        userEntity.setFirstName(userRequest.getFirstName());
        userEntity.setLastName(userRequest.getLastName());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setPassword(userRequest.getPassword());

        return new UserResponse(userRepository.save(userEntity));
    }


    public UserResponse deleteUser(long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(id);
        return new UserResponse(user);
    }


    public UserResponse patchUser(Long id, Map<String, Object> updates) {
        UserEntity user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(UserEntity.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, user, value);
            }
        });

        return new UserResponse(userRepository.save(user));
    }


    public boolean isUserExists(String email) {
        return userRepository.existsByEmail(email);
    }


    public boolean isUserExists(long id) {
        return userRepository.existsById(id);
    }

}
