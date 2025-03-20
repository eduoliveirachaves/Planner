package com.edu.planner.repositories;

import com.edu.planner.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(long id);

    boolean existsByEmail(String email);

    boolean existsById(long id);

    void deleteById(long id);
}