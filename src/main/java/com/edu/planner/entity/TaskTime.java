package com.edu.planner.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Optional;

/**
 * TaskTime class.
 * This class is an entity of the time of a task.
 * They are linked to a TaskDaySchedule entity representing the day of the week.
 * A task time has a start time and an optional end time.
 */

@Getter
@Setter
@Entity
public class TaskTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "day_schedule_id", nullable = false)
    private TaskDaySchedule taskDaySchedule;
    
    @Column(nullable = false)
    private LocalTime startTime;
    
    //if null its because dont have a duration
    private LocalTime endTime;
    
    public TaskTime (TaskDaySchedule task, LocalTime startTime, LocalTime endTime) {
        this.taskDaySchedule = task;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public TaskTime () {
    }
    
    public Long getId () {return id;}
    
    boolean hasDuration () {
        return endTime != null;
    }
    
    public LocalTime getStartTime () {
        return startTime;
    }
    
    public Optional<LocalTime> getEndTime () {
        return Optional.ofNullable(endTime);
    }
    
    public void setStartTime (LocalTime startTime) {
        this.startTime = startTime;
    }
    
    @Override
    public String toString () {
        return "TaskTime{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
    
    
}
