package com.edu.planner.mapper;

import com.edu.planner.dto.task.TaskRequest;
import com.edu.planner.dto.task.TaskResponse;
import com.edu.planner.dto.task.TaskScheduleDto;
import com.edu.planner.dto.task.TaskTimeDto;
import com.edu.planner.entity.Task;
import com.edu.planner.entity.TaskSchedule;
import com.edu.planner.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TaskMapper {
    
    public static Task toEntity (TaskRequest dto, UserEntity user) {
        return new Task(dto.title(), dto.description(), dto.startDate(), dto.dueDate(), dto.status(), dto.priority(),
                        dto.category(), dto.objective(), user);
    }
    
    public static TaskResponse toDto (Task entity) {
        return new TaskResponse(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getDueDate(),
                                entity.getStatus(), null);
    }
    
    public static TaskResponse toDto (Task entity, List<TaskSchedule> repetition) {
        List<TaskScheduleDto> responseList = repetition.stream()
                                                       .map(r -> new TaskScheduleDto(r.getDay(), r.getScheduledTimes()
                                                                                                  .stream()
                                                                                                  .map(s -> new TaskTimeDto(
                                                                                                          s.getStartTime(),
                                                                                                          s.getEndTime()
                                                                                                           .orElse(null)))
                                                                                                  .toList()))
                                                       .toList();
        return new TaskResponse(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getDueDate(),
                                entity.getStatus(), responseList);
    }
    
}
