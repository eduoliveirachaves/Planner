package com.edu.planner.repositories;

import com.edu.planner.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.edu.planner.entity.TaskEntity.Status;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, String> {

    TaskEntity findById(long id);

    List<TaskEntity> findByStatus(Status status);

    TaskEntity deleteById(long id);



}
