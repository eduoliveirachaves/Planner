package com.edu.planner.dto.task;

import com.edu.planner.utils.Enums;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalTime;

public record TaskFrequencyDto(
        @Enumerated(EnumType.STRING)
        Enums.WeekDay day,
        LocalTime startTime,
        //if null its because dont have a duration
        LocalTime endTime) {}
