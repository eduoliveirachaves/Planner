package com.edu.planner.factory;

import com.edu.planner.dto.task.TaskRequest;
import com.edu.planner.dto.task.TaskDayScheduleDto;
import com.edu.planner.dto.task.TaskTimeDto;
import com.edu.planner.entity.Task;
import com.edu.planner.entity.TaskDaySchedule;
import com.edu.planner.entity.TaskTime;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.exceptions.BadRequestException;
import com.edu.planner.mapper.TaskMapper;
import com.edu.planner.repositories.TaskRepository;
import com.edu.planner.repositories.TaskDayScheduleRepository;
import com.edu.planner.utils.Enums.TaskType;
import lombok.extern.slf4j.Slf4j;

/**
 * TaskFactory class.
 * This class is used to create a task.
 * It provides a method to create a task.
 */


@Slf4j
public class TaskFactory {
    
    public TaskFactory () {
    }
    
    // Tasks need to have a title and a type
    // One time tasks need to have a due date
    // Repeating tasks need to have a schedule
    // All the other fields are optional
    public static Task createTask (TaskRequest req, UserEntity user, TaskDayScheduleRepository taskDayScheduleRepository,
                                   TaskRepository taskRepository) {
        
        
        if (req.title() == null || req.title()
                                      .isEmpty()) {
            throw new BadRequestException("Task must have a title");
        }
        
        TaskType taskType = req.type();
        
        if (taskType == null || taskType.name()
                                        .isEmpty()) {
            throw new BadRequestException("Task must have a type");
        }
        
        Task task = TaskMapper.toEntity(req, user);
        
        if (taskType == TaskType.ONE_TIME) {
            
            if (req.dueDate() == null) {
                throw new BadRequestException("One time task must have a due date");
            }
            
            
            taskRepository.save(task);
            
        } else {
            if (req.schedule() == null) {
                throw new BadRequestException("Repeating task must have a schedule");
            }
            
            taskRepository.save(task);
            
            for (TaskDayScheduleDto repetition : req.schedule()) {
                TaskDaySchedule schedule = new TaskDaySchedule(task);
                for (TaskTimeDto t : repetition.scheduledTimes()) {
                    TaskTime time = new TaskTime(schedule, t.startTime(), t.endTime());
                    
                    schedule.getScheduledTimes()
                            .add(time);
                }
                schedule.setDay(repetition.day());
                
                taskDayScheduleRepository.save(schedule);
            }
            
        }
        return task;
    }
}
