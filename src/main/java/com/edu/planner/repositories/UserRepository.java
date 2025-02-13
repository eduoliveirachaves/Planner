package com.edu.planner.repositories;

import com.edu.planner.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findUserModelById(long id);

    User findUserModelByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsById(long id);

    User deleteById(long id);
}