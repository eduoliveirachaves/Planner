package com.edu.planner.services;

import com.edu.planner.auth.CustomUserDetails;
import com.edu.planner.dto.user.UserCredentials;
import com.edu.planner.dto.user.UserRequest;
import com.edu.planner.dto.user.UserResponse;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.exceptions.UserNotFoundException;
import com.edu.planner.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserResponse createUser(UserRequest userRequest) {
        if (isUserExists(userRequest.getEmail())) {
            throw new RuntimeException("User already exists");
        }
        String hashedPassword = passwordEncoder.encode(userRequest.getPassword());

        return new UserResponse(userRepository.save(new UserEntity(userRequest.getFirstName(), userRequest.getLastName(), userRequest.getEmail(), hashedPassword)));
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
    public UserEntity getUser(long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
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
