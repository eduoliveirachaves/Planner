package com.edu.planner.services;

import com.edu.planner.models.User;
import com.edu.planner.repositories.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String firstName, String lastName, String email, String password) {
        User user = new User(firstName, lastName, email, password);
        return userRepository.save(user);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findUserModelById(id);
    }

    public User updateUser(long id, String firstName, String lastName, String email, String password) {
        User user = userRepository.findUserModelById(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        return userRepository.save(user);
    }

    public User deleteUser(long id) {
        return userRepository.deleteById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserModelByEmail(email);
    }


    public boolean isUserExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isUserExists(long id) {
        return userRepository.existsById(id);
    }
}
