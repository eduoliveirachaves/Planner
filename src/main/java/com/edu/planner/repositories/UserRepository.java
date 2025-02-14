package com.edu.planner.repositories;

import com.edu.planner.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(long id);

    boolean existsByEmail(String email);

    boolean existsById(long id);

    void deleteById(long id);
}