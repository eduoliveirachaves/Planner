package com.edu.planner.services;

import com.edu.planner.dto.task.TaskRequest;
import com.edu.planner.dto.task.TaskResponse;
import com.edu.planner.entity.Task;
import com.edu.planner.entity.TaskSchedule;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.exceptions.BadRequestException;
import com.edu.planner.exceptions.TaskNotFoundException;
import com.edu.planner.factory.TaskFactory;
import com.edu.planner.mapper.TaskMapper;
import com.edu.planner.repositories.TaskRepository;
import com.edu.planner.repositories.TaskScheduleRepository;
import com.edu.planner.utils.Enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TaskService class.
 * This class is used to manage tasks.
 * It provides methods to create, update, delete, and retrieve tasks.
 * Used by the TaskController.
 */

@Slf4j
@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    
    private final TaskScheduleRepository taskScheduleRepository;
    
    public TaskService (TaskRepository taskRepository, TaskScheduleRepository taskScheduleRepository) {
        this.taskRepository = taskRepository;
        this.taskScheduleRepository = taskScheduleRepository;
    }
    
    // Create a new task
    public TaskResponse createTask (TaskRequest task, UserEntity user) {
        log.info("Creating a task: {}", task);
        
        Task taskEntity = TaskFactory.createTask(task, user, taskScheduleRepository, taskRepository);
        
        taskRepository.save(taskEntity);
        
        List<TaskSchedule> taskSchedules = taskScheduleRepository.findTaskRepetitionByTask(taskEntity);
        
        return TaskMapper.toDto(taskEntity, taskSchedules);
    }
    
    public List<TaskResponse> getUserTasks (UserEntity user) {
        // Map the user tasks to TaskResponse objects
        return taskRepository.findAllByOwner(user)
                             .stream()
                             .map(TaskMapper::toDto)
                             .toList();
    }
    
    public List<TaskResponse> getUserTasksRepetitions (UserEntity user) {
        List<Task> tasks = taskRepository.findAllByOwner(user);
        List<TaskResponse> taskResponses = new ArrayList<>();
        
        for (Task task : tasks) {
            taskResponses.add(TaskMapper.toDto(task, taskScheduleRepository.findTaskRepetitionByTask(task)));
        }
        
        return taskResponses;
        
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
        Task task = taskRepository.findByIdAndOwner(id, user)
                                  .orElseThrow(TaskNotFoundException::new);
        
        if (task.getStatus()
                .equals(Status.PENDING)) {
            task.setStatus(Status.COMPLETED);
        } else {
            task.setStatus(Status.PENDING);
        }
        
        return TaskMapper.toDto(taskRepository.save(task));
    }
    
    //used for update the full task
    public TaskResponse updateTask (Long id, TaskRequest taskRequest, UserEntity user) {
        Task task = taskRepository.findByIdAndOwner(id, user)
                                  .orElseThrow(TaskNotFoundException::new);
        
        task.setTitle(taskRequest.title());
        task.setDescription(taskRequest.description());
        task.setDueDate(taskRequest.dueDate());
        task.setStatus(taskRequest.status());
        
        return TaskMapper.toDto(taskRepository.save(task));
    }
    
    public TaskResponse deleteTask (long id, UserEntity user) {
        Task t = taskRepository.findByIdAndOwner(id, user)
                               .orElseThrow(TaskNotFoundException::new);
        taskRepository.deleteById(id);
        return TaskMapper.toDto(t);
    }
    
    /*
     * The methods below are used by the Admin Controller only
     */
    
    public List<Task> getUserTasks (long id) {
        return taskRepository.findAllByOwner_Id(id);
    }
    
    public Task getTaskById (Long id) {
        return taskRepository.findById(id)
                             .orElseThrow(TaskNotFoundException::new);
    }
    
    public List<Task> getAllTasks () {
        return taskRepository.findAll();
    }
}
