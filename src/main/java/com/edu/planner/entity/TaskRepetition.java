package com.edu.planner.entity;

import com.edu.planner.utils.Enums;
import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
public class TaskRepetition {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private TaskEntity task;
    
    @Enumerated(EnumType.STRING)
    public Enums.WeekDay day;
    
    private LocalTime startTime;
    
    //if null its because dont have a duration
    private LocalTime endTime;
    
    
}
