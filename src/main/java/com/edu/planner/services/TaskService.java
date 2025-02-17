package com.edu.planner.services;

import com.edu.planner.entity.TaskEntity;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.exceptions.TaskNotFoundException;
import com.edu.planner.dto.task.Task;
import com.edu.planner.exceptions.UserNotFoundException;
import com.edu.planner.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import com.edu.planner.entity.TaskEntity.Status;
import com.edu.planner.mapper.TaskMapper;
import java.util.List;
import java.util.Objects;


@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task, UserEntity user) {
        System.out.println("user: " + user);
        System.out.println("task: " + task);

        if (user == null) {
            throw new UserNotFoundException();
        }

        TaskEntity taskEntity = TaskMapper.toEntity(task, user);
        taskRepository.save(taskEntity);
        return TaskMapper.toDto(taskEntity);
    }

    public List<Task> getUserTasks(UserEntity user) {
        return taskRepository.findAllByOwner(user).stream().map(TaskMapper::toDto).toList();
    }

    //    for admin to get all tasks - to be implemented
//    public List<TaskEntity> getAllTasks() {
//        return taskRepository.findAll();
//    }

    public Task getTaskById(Long id, UserEntity user) {
        return TaskMapper.toDto(taskRepository.findByIdAndOwner(id, user).orElseThrow(TaskNotFoundException::new));
    }

    public List<Task> getTaskByStatus(UserEntity user, String status) {
        System.out.println("status: " + status);
        if (!Objects.equals(status, "PENDING") && !Objects.equals(status, "COMPLETED" )) {
            throw new RuntimeException("Invalid status");
        }
        return taskRepository.findAllByOwnerAndStatus(user, Status.valueOf(status)).stream().map(TaskMapper::toDto).toList();
    }


    public Task updateTaskStatus(Long id, UserEntity user) {
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

    public Task updateTask(Long id, Task task, UserEntity user) {
        TaskEntity t = taskRepository.findByIdAndOwner(id, user).orElseThrow(TaskNotFoundException::new);

        t.setTitle(task.getTitle());
        t.setDescription(task.getDescription());
        t.setDueDate(task.getDueDate());

        return TaskMapper.toDto(taskRepository.save(t));
    }



    public Task deleteTask(long id) {
        TaskEntity t = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        taskRepository.deleteById(id);
        return TaskMapper.toDto(t);
    }

}
