package com.edu.planner.repositories;

import com.edu.planner.entity.TaskEntity;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.utils.Enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, String> {

    Optional<TaskEntity> findById(Long id);

    Optional<TaskEntity> findByIdAndOwner(Long id, UserEntity user);

    List<TaskEntity> findByStatus(Status status);

    List<TaskEntity> findAllById(Long ownerId);

    List<TaskEntity> findAllByOwner(UserEntity user);

    void deleteById(long id);

    List<TaskEntity> findAllByOwnerAndStatus(UserEntity user, Status status);

}