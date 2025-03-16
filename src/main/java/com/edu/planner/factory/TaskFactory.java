package com.edu.planner.factory;

import com.edu.planner.dto.task.TaskRequest;
import com.edu.planner.dto.task.TaskScheduleDto;
import com.edu.planner.dto.task.TaskTimeDto;
import com.edu.planner.entity.Task;
import com.edu.planner.entity.TaskSchedule;
import com.edu.planner.entity.TaskTime;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.exceptions.BadRequestException;
import com.edu.planner.mapper.TaskMapper;
import com.edu.planner.repositories.TaskRepository;
import com.edu.planner.repositories.TaskScheduleRepository;


public class TaskFactory {
    
    public TaskFactory () {
    }
    
    public static Task createTask (TaskRequest req, UserEntity user, TaskScheduleRepository taskScheduleRepository,
                                   TaskRepository taskRepository) {
        Task task = TaskMapper.toEntity(req, user);
        
        if (req.title() == null) {
            throw new BadRequestException("Title are required");
        }
        
        if (req.dueDate() == null && req.frequency() == null) {
            throw new BadRequestException("Due date is required");
        }
        
        taskRepository.save(task);
        
        for (TaskScheduleDto repetition : req.frequency()) {
            TaskSchedule schedule = new TaskSchedule(task);
            for (TaskTimeDto t : repetition.scheduledTimes()) {
                
                TaskTime time = new TaskTime(schedule, t.startTime(), t.endTime());
                schedule.getScheduledTimes()
                        .add(time);
            }
            schedule.setDay(repetition.day());
            
            taskScheduleRepository.save(schedule);
        }
        
        return task;
    }
    
}
