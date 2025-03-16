package com.edu.planner.repositories;

import com.edu.planner.entity.Task;
import com.edu.planner.entity.TaskSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskScheduleRepository extends JpaRepository<TaskSchedule, Long> {
    
    List<TaskSchedule> findTaskRepetitionByTask(Task task);
    
    List<TaskSchedule> findTaskRepetitionByTask_Id(Long id);
}
