package com.edu.planner.entity;

import com.edu.planner.utils.Enums;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * TaskSchedule class.
 * This class is used to manage task repetitions.
 * It provides methods to create, update, delete, and retrieve task repetitions.
 */

@Getter
@Setter
@Entity
public class TaskSchedule {
    
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private Enums.WeekDay day;
    
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    
    @OneToMany(mappedBy = "taskSchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskTime> scheduledTimes;
    
    public TaskSchedule (Enums.WeekDay day, Task task) {
        this.day = day;
        this.task = task;
        this.scheduledTimes = new ArrayList<TaskTime>();
    }
    
    public TaskSchedule () {
    }
    
    public TaskSchedule (Task task) {
        this.task = task;
        this.scheduledTimes = new ArrayList<TaskTime>();
    }
    
    
    
    
}
