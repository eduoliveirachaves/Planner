package com.edu.planner.mapper;

import com.edu.planner.dto.task.Task;
import com.edu.planner.entity.TaskEntity;
import com.edu.planner.entity.UserEntity;

public class TaskMapper {

    public static TaskEntity toEntity(Task task, UserEntity user) {
        return new TaskEntity(task.getTitle(), task.getDescription(), task.getDueDate(), user);
    }

    public static Task toDto(TaskEntity taskEntity) {
        Task task = new Task(taskEntity.getTitle(), taskEntity.getDescription(), taskEntity.getDueDate());
        task.setStatus(taskEntity.getStatus());
        task.setOwner(taskEntity.getOwner().getName());
        return task;
    }
}
