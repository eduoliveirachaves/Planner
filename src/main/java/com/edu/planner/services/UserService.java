package com.edu.planner.services;

import com.edu.planner.models.UserRequest;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.models.UserResponse;
import com.edu.planner.repositories.UserRepository;
import com.edu.planner.utils.Response;
import org.springframework.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<Response> createUser(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity(userRequest.getFirstName(), userRequest.getLastName(), userRequest.getEmail(), userRequest.getPassword());

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new Response("User already exists"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new Response("User created", new UserResponse(userRepository.save(userEntity))));
    }

    public ResponseEntity<Response> getAllUsers() {
        if (userRepository.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("No users found"));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response("Users found", userRepository.findAll()
                        .stream()
                        .map(UserResponse::new).
                        toList()));
    }

    public ResponseEntity<Response> getUserById(long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("User not found"));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response("User found", new UserResponse(userRepository.findById(id))));
    }

    public ResponseEntity<Response> updateUser(long id, UserRequest userRequest) {

        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("User not found"));
        }

        UserEntity userEntity = userRepository.findById(id);
        userEntity.setFirstName(userRequest.getFirstName());
        userEntity.setLastName(userRequest.getLastName());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setPassword(userRequest.getPassword());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response("User updated", new UserResponse(userRepository.save(userEntity))));
    }

    public UserEntity deleteUser(long id) {
        return userRepository.deleteById(id);
    }

    public boolean isUserExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isUserExists(long id) {
        return userRepository.existsById(id);
    }


    public ResponseEntity<Response> patchUser(Long id, Map<String, Object> updates) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("User not exists"));
        }

        UserEntity user = userRepository.findById(id);

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(UserEntity.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, user, value);
            }
        });

        return ResponseEntity.status(HttpStatus.OK).body(new Response("User updated",userRepository.save(user)));
    }

}
