package com.edu.planner.repositories;

import com.edu.planner.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsById(long id);

    UserEntity deleteById(long id);

    UserEntity findById(long id);
}