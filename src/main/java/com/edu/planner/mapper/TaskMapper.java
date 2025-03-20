package com.edu.planner.mapper;

import com.edu.planner.dto.task.TaskDayScheduleDto;
import com.edu.planner.dto.task.TaskRequest;
import com.edu.planner.dto.task.TaskResponse;
import com.edu.planner.dto.task.TaskTimeDto;
import com.edu.planner.entity.Task;
import com.edu.planner.entity.TaskDaySchedule;
import com.edu.planner.entity.TaskTime;
import com.edu.planner.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * TaskMapper class.
 * This class is used to map task objects.
 * It provides methods to map task objects to task dto objects and vice versa.
 */

@Slf4j
public class TaskMapper {
    
    public static Task toEntity (TaskRequest dto, UserEntity user) {
        return new Task(dto.title(), dto.description(), dto.startDate(), dto.dueDate(), dto.status(), dto.priority(),
                        dto.category(), dto.objective(), dto.type(), user);
    }
    
    // Map the task schedule dto to a TaskDaySchedule entity
    public static TaskDaySchedule toEntity (TaskDayScheduleDto dto, Task task) {
        TaskDaySchedule schedule = new TaskDaySchedule(dto.day(), task);
        
        for (TaskTimeDto time : dto.scheduledTimes()) {
            schedule.getScheduledTimes()
                    .add(new TaskTime(schedule, time.startTime(), time.endTime()));
        }
        
        return schedule;
    }
    
    public static TaskResponse toDto (Task entity) {
        return new TaskResponse(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getTaskType(),
                                entity.getDueDate(), entity.getStatus(), null);
    }
    
    // Map the task entity to a TaskResponse object
    public static TaskResponse toDto (Task entity, List<TaskDaySchedule> repetition) {
        List<TaskDayScheduleDto> responseList = repetition.stream()
                                                          // Map the TaskDaySchedule entity to a TaskDayScheduleDto object
                                                          .map(r -> new TaskDayScheduleDto(r.getDay(),
                                                                                           r.getScheduledTimes()
                                                                                            .stream() // Map the TaskTime entity to a TaskTimeDto object
                                                                                            .map(s -> new TaskTimeDto(
                                                                                                    s.getStartTime(),
                                                                                                    s.getEndTime()
                                                                                                     .orElse(null)))
                                                                                            .toList())) // Collect the TaskTimeDto objects into a list
                                                          .toList(); // Collect the TaskDayScheduleDto objects into a list
        return new TaskResponse(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getTaskType(), entity.getDueDate(),
                                entity.getStatus(), responseList);
    }
    
}
