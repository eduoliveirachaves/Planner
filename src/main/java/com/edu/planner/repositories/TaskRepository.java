package com.edu.planner.repositories;

import com.edu.planner.entity.Task;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.utils.Enums;
import com.edu.planner.utils.Enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @NonNull
    Optional<Task> findById(@NonNull Long id);

    Optional<Task> findByIdAndOwner(Long id, UserEntity user);

    List<Task> findByStatus(Status status);

    List<Task> findAllById(Long ownerId);

    List<Task> findAllByOwner(UserEntity user);

    List<Task> findAllByOwner_Id(long id);
    
    List<Task> findAllByOwnerAndTaskType(UserEntity user, Enums.TaskType type);

    void deleteById(long id);

    List<Task> findAllByOwnerAndStatus(UserEntity user, Status status);

}