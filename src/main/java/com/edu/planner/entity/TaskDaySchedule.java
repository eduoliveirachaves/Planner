package com.edu.planner.entity;

import com.edu.planner.utils.Enums;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * TaskDaySchedule class.
 * This class is used to manage task repetitions.
 * They work as a schedule for the task.
 * Ideally, a task should have a schedule for each day of the week or a maximum of 7 schedules.
 */

@Getter
@Setter
@Entity
public class TaskDaySchedule {
    
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private Enums.WeekDay day;
    
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    
    @OneToMany(mappedBy = "taskDaySchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskTime> scheduledTimes;
    
    public TaskDaySchedule (Enums.WeekDay day, Task task) {
        this.day = day;
        this.task = task;
        this.scheduledTimes = new ArrayList<TaskTime>();
    }
    
    public TaskDaySchedule () {
    }
    
    public TaskDaySchedule (Task task) {
        this.task = task;
        this.scheduledTimes = new ArrayList<TaskTime>();
    }
    
    @Override
    public String toString () {
        return "TaskDaySchedule{" +
                "id=" + id +
                ", day=" + day +
                ", task=" + task +
                ", scheduledTimes=" + scheduledTimes +
                '}';
    }
    
    
}
