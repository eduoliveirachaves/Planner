package com.edu.planner.services;

import com.edu.planner.dto.task.TaskRequest;
import com.edu.planner.dto.task.TaskResponse;
import com.edu.planner.entity.TaskEntity;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.exceptions.BadRequestException;
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
    
    
    public TaskService (TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    
    // Create a new task
    public TaskResponse createTask (TaskRequest task, UserEntity user) {
        if (user == null) {
            throw new UserNotFoundException();
        }
        
        if (task.title() == null || task.dueDate() == null) {
            throw new BadRequestException("Title and Date are required");
        }
        
        
        TaskEntity taskEntity = TaskMapper.toEntity(task, user);
        assert taskEntity != null;
        taskRepository.save(taskEntity);
        return TaskMapper.toDto(taskEntity);
    }
    
    
    public List<TaskResponse> getUserTasks (UserEntity user) {
        // Map the user tasks to TaskResponse objects
        return taskRepository.findAllByOwner(user)
                             .stream()
                             .map(TaskMapper::toDto)
                             .toList();
    }
    
    
    public TaskResponse getTaskById (Long id, UserEntity user) {
        return TaskMapper.toDto(taskRepository.findByIdAndOwner(id, user)
                                              .orElseThrow(TaskNotFoundException::new));
    }
    
    
    public List<TaskResponse> getTaskByStatus (UserEntity user, String status) {
        status = status.toUpperCase();
        if (!Objects.equals(status, "PENDING") && !Objects.equals(status, "COMPLETED")) {
            throw new BadRequestException("Invalid STATUS value");
        }
        return taskRepository.findAllByOwnerAndStatus(user, Status.valueOf(status))
                             .stream()
                             .map(TaskMapper::toDto)
                             .toList();
    }
    
    
    //change taskStatus
    public TaskResponse updateTaskStatus (Long id, UserEntity user) {
        TaskEntity taskEntity = taskRepository.findByIdAndOwner(id, user)
                                              .orElseThrow(TaskNotFoundException::new);
        
        
        if (taskEntity.getStatus()
                      .equals(Status.PENDING)) {
            taskEntity.setStatus(Status.COMPLETED);
        } else {
            taskEntity.setStatus(Status.PENDING);
        }
        
        return TaskMapper.toDto(taskRepository.save(taskEntity));
    }
    
    
    //used for update the full task
    public TaskResponse updateTask (Long id, TaskRequest taskRequest, UserEntity user) {
        TaskEntity taskEntity = taskRepository.findByIdAndOwner(id, user)
                                              .orElseThrow(TaskNotFoundException::new);
        
        taskEntity.setTitle(taskRequest.title());
        taskEntity.setDescription(taskRequest.description());
        taskEntity.setDueDate(taskRequest.dueDate());
        taskEntity.setStatus(taskRequest.status());
        
        return TaskMapper.toDto(taskRepository.save(taskEntity));
    }
    
    
    public TaskResponse deleteTask (long id, UserEntity user) {
        TaskEntity t = taskRepository.findByIdAndOwner(id, user)
                                     .orElseThrow(TaskNotFoundException::new);
        taskRepository.deleteById(id);
        return TaskMapper.toDto(t);
    }
    
    /*
     * The methods below are used by the Admin Controller only
     */
    
    
    public List<TaskEntity> getUserTasks (long id) {
        return taskRepository.findAllByOwner_Id(id);
    }
    
    
    public TaskEntity getTaskById (Long id) {
        return taskRepository.findById(id)
                             .orElseThrow(TaskNotFoundException::new);
    }
    
    
    public List<TaskEntity> getAllTasks () {
        return taskRepository.findAll();
    }
}
