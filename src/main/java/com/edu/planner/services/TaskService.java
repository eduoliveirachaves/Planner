package com.edu.planner.services;

import com.edu.planner.dto.task.TaskDayScheduleDto;
import com.edu.planner.dto.task.TaskRequest;
import com.edu.planner.dto.task.TaskResponse;
import com.edu.planner.entity.Task;
import com.edu.planner.entity.TaskDaySchedule;
import com.edu.planner.entity.TaskTime;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.exceptions.BadRequestException;
import com.edu.planner.exceptions.TaskNotFoundException;
import com.edu.planner.factory.TaskFactory;
import com.edu.planner.mapper.TaskMapper;
import com.edu.planner.repositories.TaskDayScheduleRepository;
import com.edu.planner.repositories.TaskRepository;
import com.edu.planner.utils.Enums;
import com.edu.planner.utils.Enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * This class is used to manage tasks.
 * It provides methods to create, update, delete, and retrieve tasks.
 * Order of methods: GET, POST, PUT, DELETE
 * Used by the TaskController and AdminController.
 */

@Slf4j
@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    
    private final TaskDayScheduleRepository taskDayScheduleRepository;
    
    public TaskService (TaskRepository taskRepository, TaskDayScheduleRepository taskDayScheduleRepository) {
        this.taskRepository = taskRepository;
        this.taskDayScheduleRepository = taskDayScheduleRepository;
    }
    
    // Get all tasks for a user
    public List<TaskResponse> getUserTasks (UserEntity user) {
        List<Task> tasks = taskRepository.findAllByOwner(user);
        
        if (tasks.isEmpty()) {
            return Collections.emptyList();
        }
        
        return tasks.stream()
                    .map(this::toDto)
                    .toList();
    }
    
    // Get a task by id
    // If the task does not exist, return an error message
    // If the task does not belong to the user, return an error message
    public TaskResponse getTaskById (Long id, UserEntity user) {
        Task task = taskRepository.findByIdAndOwner(id, user)
                                  .orElseThrow(TaskNotFoundException::new);
        return this.toDto(task);
    }
    
    // Get all one-time tasks for a user
    // If no one-time tasks are found, return an empty list
    public List<TaskResponse> getOneTimeTasks(UserEntity user) {
        List<Task> tasks = taskRepository.findAllByOwnerAndTaskType(user, Enums.TaskType.ONE_TIME);
        
        if (tasks.isEmpty()) {
            return Collections.emptyList();
        }
        
        return tasks.stream()
                    .map(this::toDto)
                    .toList();
    }
    
    
    // Get all tasks with a specific status
    // If the status is not PENDING or COMPLETED, return an error message
    // If no tasks are found with the specified status, return an error message
    public List<TaskResponse> getTaskByStatus (UserEntity user, Status status) {
        List<Task> tasks = taskRepository.findAllByOwnerAndStatus(user, status);
        
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("No tasks found with status " + status);
        }
        
        return tasks.stream()
                    .map(this::toDto)
                    .toList();
    }
    
    // Create a new task
    // Uses the TaskFactory to create a new task
    public TaskResponse createTask (TaskRequest task, UserEntity user) {
        Task taskEntity = TaskFactory.createTask(task, user, taskDayScheduleRepository, taskRepository);
        
        return this.toDto(taskEntity);
    }
    
    // Create a new schedule for a existing task
    // If the task does not exist, return an error message
    // If the task already has a schedule, return an error message
    public TaskResponse createTaskSchedule (Long id, List<TaskDayScheduleDto> schedules, UserEntity user) {
        Task task = taskRepository.findByIdAndOwner(id, user)
                                  .orElseThrow(TaskNotFoundException::new);
        
        task.setTaskType(Enums.TaskType.REPEATING);
        
        List<TaskDaySchedule> newSchedules = schedules.stream()
                                                      .map(s -> TaskMapper.toEntity(s, task))
                                                      .toList();
        
        taskDayScheduleRepository.saveAll(newSchedules);
        
        return this.toDto(taskRepository.save(task));
    }
    
    // Update the status of a task
    // If the task does not exist, return an error message
    public TaskResponse updateTaskStatus (Long id, UserEntity user) {
        Task task = taskRepository.findByIdAndOwner(id, user)
                                  .orElseThrow(TaskNotFoundException::new);
        
        if (task.getStatus()
                .equals(Status.PENDING)) {
            task.setStatus(Status.COMPLETED);
        } else {
            task.setStatus(Status.PENDING);
        }
        
        return this.toDto(taskRepository.save(task));
    }
    
    //used for update the full task
    public TaskResponse updateTask (Long id, TaskRequest request, UserEntity user) {
        Task task = taskRepository.findByIdAndOwner(id, user)
                                  .orElseThrow(TaskNotFoundException::new);
        
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setCategory(request.category());
        task.setPriority(request.priority());
        task.setObjective(request.objective());
        task.setDueDate(request.dueDate());
        task.setStatus(request.status());
        task.setStartDate(request.startDate());
        
        return this.toDto(taskRepository.save(task));
    }
    
    //used for update the schedule of a task // CHANGE the schedule of a task // DELETE the schedule of a task
    public TaskResponse updateTaskSchedule (Long id, List<TaskDayScheduleDto> newSchedules, UserEntity user) {
        Task task = taskRepository.findByIdAndOwner(id, user)
                                  .orElseThrow(TaskNotFoundException::new);
        
        List<TaskDaySchedule> schedulesList = this.getTaskSchedule(task);
        
        if (schedulesList.isEmpty()) {
            throw new BadRequestException("Task does not have a schedules");
        } else {
            taskDayScheduleRepository.deleteAll(schedulesList);
        }
        
        schedulesList = newSchedules.stream()
                                    .map(s -> TaskMapper.toEntity(s, task))
                                    .toList();
        
        taskDayScheduleRepository.saveAll(schedulesList);
        
        return this.toDto(task);
    }
    
    // Update the schedule of a task for a specific day
    // if the new schedule is empty, return an error message
    // If the task does not have a schedule, return an error message
    // If the task does not have a schedule for the specified day, return an error message
    // If the task has a schedule for the specified day, update the schedule with the new schedule
    public TaskResponse updateTaskScheduleDay (Long id, TaskDayScheduleDto schedule, UserEntity user) {
        
        if (schedule.scheduledTimes()
                    .isEmpty()) {
            throw new BadRequestException("Schedule cannot be empty");
        }
        
        Task task = taskRepository.findByIdAndOwner(id, user)
                                  .orElseThrow(TaskNotFoundException::new);
        
        List<TaskDaySchedule> schedulesList = this.getTaskSchedule(task);
        
        if (schedulesList.isEmpty()) {
            throw new BadRequestException("Task does not have a schedules");
        }
        
        TaskDaySchedule taskDaySchedule = schedulesList.stream()
                                                       .filter(s -> s.getDay()
                                                                     .equals(schedule.day()))
                                                       .findFirst()
                                                       .orElseThrow(() -> new BadRequestException(
                                                               "Task does not have a schedule for this day"));
        
        List<TaskTime> times = taskDaySchedule.getScheduledTimes();
        
        times.clear();
        
        schedule.scheduledTimes()
                .forEach(t -> times.add(new TaskTime(taskDaySchedule, t.startTime(), t.endTime())));
        
        taskDayScheduleRepository.save(taskDaySchedule);
        
        return this.toDto(task);
    }
    
    // Delete a task
    public String deleteTask (long id, UserEntity user) {
        Task t = taskRepository.findByIdAndOwner(id, user)
                               .orElseThrow(TaskNotFoundException::new);
        taskRepository.deleteById(id);
        return "Deleted task with id: " + id;
    }
    
    // Delete the schedule of a task
    // If the task does not have a schedule, return an error message
    public TaskResponse deleteTaskSchedule (Long id, UserEntity user) {
        Task task = taskRepository.findByIdAndOwner(id, user)
                                  .orElseThrow(TaskNotFoundException::new);
        
        List<TaskDaySchedule> schedulesList = this.getTaskSchedule(task);
        
        if (schedulesList.isEmpty()) {
            throw new BadRequestException("Task does not have a schedules");
        }
        
        taskDayScheduleRepository.deleteAll(schedulesList);
        
        task.setTaskType(Enums.TaskType.ONE_TIME);
        
        return this.toDto(taskRepository.save(task));
    }
    
    // Delete the schedule of a task for a specific day
    // If the task does not have a schedule, return an error message
    public TaskResponse deleteTaskScheduleDay (Long id, Enums.WeekDay day, UserEntity user) {
        Task task = taskRepository.findByIdAndOwner(id, user)
                                  .orElseThrow(TaskNotFoundException::new);
        
        List<TaskDaySchedule> schedulesList = this.getTaskSchedule(task);
        
        if (schedulesList.isEmpty()) {
            throw new BadRequestException("Task does not have a schedules");
        }
        
        TaskDaySchedule taskDaySchedule = schedulesList.stream()
                                                       .filter(s -> s.getDay()
                                                                     .equals(day))
                                                       .findFirst()
                                                       .orElseThrow(() -> new BadRequestException(
                                                               "Task does not have a schedule for this day"));
        
        taskDayScheduleRepository.delete(taskDaySchedule);
        
        return this.toDto(task);
    }
    
    /*
     * The methods below are used by the Task Service only
     * Helper methods
     */
    
    // Get the schedule of a task
    // If the task does not have a schedule, return an empty list
    private List<TaskDaySchedule> getTaskSchedule (Task task) {
        List<TaskDaySchedule> taskDaySchedule = taskDayScheduleRepository.findTaskScheduleByTask(task);
        
        if (taskDaySchedule == null || taskDaySchedule.isEmpty()) {
            return Collections.emptyList();
        }
        
        return taskDaySchedule;
    }
    
    // Convert Task entity to TaskResponse object
    // If the task has a schedule, return the TaskResponse object with the schedule
    private TaskResponse toDto (Task task) {
        List<TaskDaySchedule> taskDaySchedules = this.getTaskSchedule(task);
        return (taskDaySchedules.isEmpty()) ? TaskMapper.toDto(task) : TaskMapper.toDto(task, taskDaySchedules);
    }
    
    
    /*
     * The methods below are used by the Admin Controller only
     */
    
    // Get all tasks of user with id = id
    public List<Task> getUserTasks (long id) {
        return taskRepository.findAllByOwner_Id(id);
    }
    
    // Get task with id = id
    public Task getTaskById (Long id) {
        return taskRepository.findById(id)
                             .orElseThrow(TaskNotFoundException::new);
    }
    
    // Get ALL tasks
    public List<Task> getAllTasks () {
        return taskRepository.findAll();
    }
    
    // Update task with id = id
    public Task updateTask (Task task) {
        return taskRepository.save(task);
    }
    
    // Delete task with id = id
    public void deleteTask (long id) {
        taskRepository.deleteById(id);
    }
}
