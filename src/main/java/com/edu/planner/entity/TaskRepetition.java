package com.edu.planner.entity;

import com.edu.planner.utils.Enums;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

/**
 * TaskRepetition class.
 * This class is used to manage task repetitions.
 * It provides methods to create, update, delete, and retrieve task repetitions.
 */

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class TaskRepetition {
    
    @Enumerated(EnumType.STRING)
    public Enums.WeekDay day;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private TaskEntity task;
    
    private LocalTime startTime;
    
    //if null its because dont have a duration
    private LocalTime endTime;
    
    
}
