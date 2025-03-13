package com.edu.planner.mapper;

import com.edu.planner.dto.task.TaskFrequencyDto;
import com.edu.planner.dto.task.TaskRequest;
import com.edu.planner.dto.task.TaskResponse;
import com.edu.planner.entity.TaskEntity;
import com.edu.planner.entity.TaskRepetition;
import com.edu.planner.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TaskMapper {
    
    public static TaskEntity toEntity (TaskRequest request, UserEntity user) {
        TaskEntity entity = new TaskEntity();
        
        try {
            
            entity.setOwner(user);
            entity.setTitle(request.title());
            entity.setDescription(request.description());
            
            entity.setStatus(request.status());
            
            entity.setDueDate(request.dueDate());
            entity.setCategory(request.category());
            
            
        } catch (Exception err) {
            log.error("e: ", err);
        }
        
        return entity;
    }
    
    public static TaskResponse toDto (TaskEntity entity) {
        return new TaskResponse(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getDueDate(),
                                entity.getStatus(), null);
    }
    
    public static TaskResponse toDto (TaskEntity entity, List<TaskRepetition> repetition) {
        List<TaskFrequencyDto> responseList = repetition.stream()
                                                      .map(r -> new TaskFrequencyDto(r.getDay(), r.getStartTime(), r.getEndTime()))
                                                      .toList();
        return new TaskResponse(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getDueDate(), entity.getStatus(), responseList);
    }
    
    public static TaskRepetition toTaskRepetition (TaskFrequencyDto taskSchedule, TaskEntity task) {
        TaskRepetition taskRepetition = new TaskRepetition();
        try {
            taskRepetition.setTask(task);
            taskRepetition.setDay(taskSchedule.day());
            taskRepetition.setStartTime(taskSchedule.startTime());
            taskRepetition.setEndTime(taskSchedule.endTime());
        } catch (Exception err) {
            log.error("e: ", err);
        }
        
        return taskRepetition;
    }
}
