package com.edu.planner.repositories;

import com.edu.planner.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import com.edu.planner.models.Task.Status;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, String> {

    Task findById(long id);

    List<Task> findByStatus(Status status);

    Task deleteById(long id);



}
