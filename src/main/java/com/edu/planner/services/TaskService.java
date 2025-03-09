package com.edu.planner.services;

import com.edu.planner.dto.task.TaskRequest;
import com.edu.planner.dto.task.TaskResponse;
import com.edu.planner.entity.TaskEntity;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.exceptions.TaskNotFoundException;
import com.edu.planner.exceptions.UserNotFoundException;
import com.edu.planner.mapper.TaskMapper;
import com.edu.planner.repositories.TaskRepository;
import com.edu.planner.utils.Enums.Status;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * TaskService class.
 * This class is used to manage tasks.
 * It provides methods to create, update, delete, and retrieve tasks.
 * Used by the TaskController.
 */

@Service
public class TaskService {

    private final TaskRepository taskRepository;


    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public TaskResponse createTask(TaskRequest task, UserEntity user) {
        if (user == null) {
            throw new UserNotFoundException();
        }


        TaskEntity taskEntity = TaskMapper.toEntity(task, user);
        assert taskEntity != null;
        taskRepository.save(taskEntity);
        return TaskMapper.toDto(taskEntity);
    }


    public List<TaskResponse> getUserTasks(UserEntity user) {
        return taskRepository.findAllByOwner(user).stream().map(TaskMapper::toDto).toList();
    }

    //    for admin to get all tasks - to be implemented
//    public List<TaskEntity> getAllTasks() {
//        return taskRepository.findAll();
//    }


    public TaskResponse getTaskById(Long id, UserEntity user) {
        return TaskMapper.toDto(taskRepository.findByIdAndOwner(id, user).orElseThrow(TaskNotFoundException::new));
    }


    public List<TaskResponse> getTaskByStatus(UserEntity user, String status) {
        System.out.println("status: " + status);
        if (!Objects.equals(status, "PENDING") && !Objects.equals(status, "COMPLETED")) {
            throw new RuntimeException("Invalid status");
        }
        return taskRepository.findAllByOwnerAndStatus(user, Status.valueOf(status)).stream().map(TaskMapper::toDto).toList();
    }


    public TaskResponse updateTaskStatus(Long id, UserEntity user) {
        TaskEntity taskEntity = taskRepository.findByIdAndOwner(id, user).orElseThrow(TaskNotFoundException::new);
        if (taskEntity == null) {
            throw new RuntimeException("TaskEntity not found");
        }

        if (taskEntity.getStatus().equals(Status.PENDING)) {
            taskEntity.setStatus(Status.COMPLETED);
        } else {
            taskEntity.setStatus(Status.PENDING);
        }

        return TaskMapper.toDto(taskRepository.save(taskEntity));
    }


    public TaskResponse updateTask(Long id, TaskResponse taskResponse, UserEntity user) {
        TaskEntity t = taskRepository.findByIdAndOwner(id, user).orElseThrow(TaskNotFoundException::new);

        t.setTitle(taskResponse.getTitle());
        t.setDescription(taskResponse.getDescription());
        t.setDueDate(taskResponse.getDueDate());

        return TaskMapper.toDto(taskRepository.save(t));
    }


    public TaskResponse deleteTask(long id) {
        TaskEntity t = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        taskRepository.deleteById(id);
        return TaskMapper.toDto(t);
    }

}
