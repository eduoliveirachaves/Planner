package com.edu.planner.services;

import com.edu.planner.auth.CustomUserDetails;
import com.edu.planner.auth.JwtService;
import com.edu.planner.dto.user.UserRequest;
import com.edu.planner.dto.user.UserResponse;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.exceptions.UserNotFoundException;
import com.edu.planner.mapper.UserMapper;
import com.edu.planner.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final JwtService jwtService;


    public UserService(UserRepository userRepository, AuthService authService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }


    public UserResponse createUser(UserRequest userRequest) {
        if (isUserExists(userRequest.getEmail())) {
            throw new RuntimeException("User already exists");
        }

        String hashedPassword = jwtService.getHashedPassword(userRequest.getPassword());

        userRequest.setPassword(hashedPassword);

        return UserMapper.toUserResponse(userRepository.save(UserMapper.toUserEntity(userRequest)));
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


    public UserResponse getUserById(long id) {
        return UserMapper.toUserResponse(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }


    //old method
    public UserEntity getUser(long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }


    public UserResponse updateUser(long id, UserRequest userRequest) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        userEntity.setFirstName(userRequest.getFirstName());
        userEntity.setLastName(userRequest.getLastName());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setPassword(userRequest.getPassword());

        return UserMapper.toUserResponse(userRepository.save(userEntity));
    }


    public UserResponse deleteUser(long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(id);
        return UserMapper.toUserResponse(user);
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

        return UserMapper.toUserResponse(userRepository.save(user));
    }


    public boolean isUserExists(String email) {
        return userRepository.existsByEmail(email);
    }


    public boolean isUserExists(long id) {
        return userRepository.existsById(id);
    }


    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Default method to load user by username (email or username)
        return userRepository.findByEmail(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }


    public UserDetails loadUserById(Long userId) {
        return userRepository.findById(userId)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));
    }

}
