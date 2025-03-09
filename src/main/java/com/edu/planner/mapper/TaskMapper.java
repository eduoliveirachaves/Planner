package com.edu.planner.mapper;

import com.edu.planner.dto.task.TaskRequest;
import com.edu.planner.dto.task.TaskResponse;
import com.edu.planner.entity.TaskEntity;
import com.edu.planner.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskMapper {

    public static TaskEntity toEntity(TaskRequest task, UserEntity user) {
        try {
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setOwner(user);
            taskEntity.setTitle(task.getTitle());
            taskEntity.setDescription(task.getDescription());
            taskEntity.setDueDate(task.getDate());
            return taskEntity;
        } catch (Exception err) {
            log.error("e: ", err);

            return null;
        }
    }

    public static TaskResponse toDto(TaskEntity taskEntity) {
        TaskResponse taskResponse = new TaskResponse(taskEntity.getTitle(), taskEntity.getDescription(), taskEntity.getDueDate());
        taskResponse.setStatus(taskEntity.getStatus());
        taskResponse.setOwner(taskEntity.getOwner().getName());
        return taskResponse;
    }
}
