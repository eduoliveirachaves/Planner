package com.edu.planner.dto.task;

import com.edu.planner.utils.Enums;

import java.time.LocalDate;

public record TaskUpdate(
        String title,
        String description,
        LocalDate date,
        Enums.Status status) {}
    