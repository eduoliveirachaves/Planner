package com.edu.planner.services;

import com.edu.planner.auth.JwtService;
import com.edu.planner.dto.user.UserRequest;
import com.edu.planner.dto.user.UserResponse;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.exceptions.UserNotFoundException;
import com.edu.planner.mapper.UserMapper;
import com.edu.planner.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * UserService class.
 * This class is used to manage users.
 * It provides methods to create, update, delete, and retrieve users.
 * Used by the UserController.
 */

@Service
public class UserService {

    private final UserRepository userRepository;

    private final JwtService jwtService;


    public UserService(UserRepository userRepository, AuthService authService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }


    public UserResponse createUser(UserRequest userRequest) {
        if (userRequest.getEmail() == null || userRequest.getPassword() == null || userRequest.getName() == null) {
            throw new RuntimeException("Email, password and name are required");
        }

        if (isUserExists(userRequest.getEmail())) {
            throw new RuntimeException("User already exists");
        }

        String hashedPassword = jwtService.getHashedPassword(userRequest.getPassword());

        userRequest.setPassword(hashedPassword);

        return UserMapper.toUserResponse(userRepository.save(UserMapper.toUserEntity(userRequest)));
    }


    public UserResponse getUserById(long id) {
        return UserMapper.toUserResponse(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }


    public List<UserResponse> getAllUsers() {
        if (userRepository.findAll().isEmpty()) {
            throw new RuntimeException("No users found");
        }

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserResponse).
                toList();
    }


    //can be useful for admin only
    public UserEntity getUser(long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }


    public UserResponse updateUser(UserEntity userEntity, UserRequest userRequest) {
        userEntity.setName(userRequest.getName());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setPassword(userRequest.getPassword());
        userEntity.setAge(userRequest.getAge());
        userEntity.setHeight(userRequest.getHeight());
        userEntity.setWeight(userRequest.getWeight());
        userEntity.setGender(userRequest.getGender());

        return UserMapper.toUserResponse(userRepository.save(userEntity));
    }


    public String updateUserPassword(UserEntity userEntity, String password) {
        userEntity.setPassword(jwtService.getHashedPassword(password));
        userRepository.save(userEntity);
        return "Password updated successfully";
    }


    //patch user - update only the fields that are provided // NOT PATCHING PASSWORD
    public UserResponse patchUser(UserEntity user, Map<String, Object> updates) {
        Set<String> allowedUpdates = Set.of("name", "email", "age", "height", "weight", "gender");
        updates.forEach((key, value) -> {
            if (!allowedUpdates.contains(key)) {
                throw new RuntimeException("Invalid field: " + key);
            }
            Field field = ReflectionUtils.findField(UserEntity.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, user, value);
            }
        });

        return UserMapper.toUserResponse(userRepository.save(user));
    }


    //admin only
    public UserResponse deleteUser(long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(id);
        return UserMapper.toUserResponse(user);
    }


    //auto delete
    public UserResponse deleteUser(UserEntity user) {
        userRepository.deleteById(user.getId());
        return UserMapper.toUserResponse(user);
    }


    //helpers below


    public boolean isUserExists(String email) {
        return userRepository.existsByEmail(email);
    }


    public boolean isUserExists(long id) {
        return userRepository.existsById(id);
    }


    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    //test method
    public UserEntity getUserByToken(String token) {
        return userRepository.findById(jwtService.extractUserId(token))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
