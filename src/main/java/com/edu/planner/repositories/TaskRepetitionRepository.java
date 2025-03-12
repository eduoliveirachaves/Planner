package com.edu.planner.repositories;

import com.edu.planner.entity.TaskEntity;
import com.edu.planner.entity.TaskRepetition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepetitionRepository extends JpaRepository<TaskRepetition, Long> {
    
    List<TaskRepetition> findTaskRepetitionByTask(TaskEntity task);
    
    List<TaskRepetition> findTaskRepetitionByTask_Id(Long id);
}
