package com.edu.planner.repositories;

import com.edu.planner.entity.Task;
import com.edu.planner.entity.TaskDaySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDayScheduleRepository extends JpaRepository<TaskDaySchedule, Long> {
    
    List<TaskDaySchedule> findTaskScheduleByTask (Task task);
    
    List<TaskDaySchedule> findTaskScheduleById (Long id);
    
}
