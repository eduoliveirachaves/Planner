package com.edu.planner.services;

import com.edu.planner.models.Task;
import com.edu.planner.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import com.edu.planner.models.Task.Status;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(long id) {
        return taskRepository.findById(id);
    }

    public List<Task> getTaskByStatus(String status) {

        return taskRepository.findByStatus(Status.PENDING);
    }


    public Task updateTaskStatus(long id) {
        Task task = taskRepository.findById(id);
        if (task == null) {
            throw new RuntimeException("Task not found");
        }

        if (task.getStatus().equals(Status.PENDING)) {
            task.setStatus(Status.COMPLETED);
        } else {
            task.setStatus(Status.PENDING);
        }

        return taskRepository.save(task);
    }

    public Task updateTask(long id, String title, String description, LocalDateTime dueDate) {
        Task t = taskRepository.findById(id);
        t.setTitle(title);
        t.setDescription(description);

        return taskRepository.save(t);
    }

    public Task deleteTask(long id) {
        return taskRepository.deleteById(id);
    }



}
