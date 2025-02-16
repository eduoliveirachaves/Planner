package com.edu.planner.services;

import com.edu.planner.entity.TaskEntity;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.exceptions.TaskNotFoundException;
import com.edu.planner.dto.task.Task;
import com.edu.planner.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import com.edu.planner.entity.TaskEntity.Status;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final UserService userService;


    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public TaskEntity createTask(Task task, UserEntity user) {
        if (user == null) {
            throw new RuntimeException("ERROR IS HERE - User not found");
        }
        TaskEntity taskEntity = new TaskEntity(task);
        taskEntity.setOwner(user);
        return taskRepository.save(taskEntity);
    }

    public List<TaskEntity> getAllTasks() {
        return taskRepository.findAll();
    }

    public TaskEntity getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    }

    public List<TaskEntity> getTaskByStatus(Status status) {
        return taskRepository.findByStatus(status);
    }


    public TaskEntity updateTaskStatus(long id) {
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        if (taskEntity == null) {
            throw new RuntimeException("TaskEntity not found");
        }

        if (taskEntity.getStatus().equals(Status.PENDING)) {
            taskEntity.setStatus(Status.COMPLETED);
        } else {
            taskEntity.setStatus(Status.PENDING);
        }

        return taskRepository.save(taskEntity);
    }

    public TaskEntity updateTask(long id, String title, String description, LocalDateTime dueDate) {
        TaskEntity t = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        t.setTitle(title);
        t.setDescription(description);

        return taskRepository.save(t);
    }

    public TaskEntity deleteTask(long id) {
        TaskEntity t = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        taskRepository.deleteById(id);
        return t;
    }



}
