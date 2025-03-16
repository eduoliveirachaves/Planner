package com.edu.planner.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Optional;

@Getter
@Setter
@Entity
public class TaskTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "shedule_id", nullable = false)
    private TaskSchedule taskSchedule;
    
    private LocalTime startTime;
    
    //if null its because dont have a duration
    private LocalTime endTime;
    
    public TaskTime (TaskSchedule task, LocalTime startTime, LocalTime endTime) {
        this.taskSchedule = task;
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
    
    
}
