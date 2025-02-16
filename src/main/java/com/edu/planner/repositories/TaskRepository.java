package com.edu.planner.repositories;

import com.edu.planner.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.edu.planner.entity.TaskEntity.Status;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, String> {

    Optional<TaskEntity> findById(Long id);

    List<TaskEntity> findByStatus(Status status);

    List<TaskEntity> findAllById(Long ownerId);



    void deleteById(long id);



}
