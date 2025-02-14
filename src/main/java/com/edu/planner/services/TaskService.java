package com.edu.planner.services;

import com.edu.planner.entity.TaskEntity;
import com.edu.planner.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import com.edu.planner.entity.TaskEntity.Status;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskEntity createTask(TaskEntity taskEntity) {
        return taskRepository.save(taskEntity);
    }

    public List<TaskEntity> getAllTasks() {
        return taskRepository.findAll();
    }

    public TaskEntity getTaskById(long id) {
        return taskRepository.findById(id);
    }

    public List<TaskEntity> getTaskByStatus(String status) {

        return taskRepository.findByStatus(Status.PENDING);
    }


    public TaskEntity updateTaskStatus(long id) {
        TaskEntity taskEntity = taskRepository.findById(id);
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
        TaskEntity t = taskRepository.findById(id);
        t.setTitle(title);
        t.setDescription(description);

        return taskRepository.save(t);
    }

    public TaskEntity deleteTask(long id) {
        return taskRepository.deleteById(id);
    }



}
