package com.edu.planner.dto.task;

import com.edu.planner.utils.Enums.Status;

import java.time.LocalDate;
import java.util.List;

/**
 * This class represents a task response.
 * Used in the TaskController to send the task back.
 */


public record TaskResponse(
        Long id,
        String title,
        String description,
        LocalDate dueDate,
        Status status,
        List<TaskScheduleDto> frequency) {
    
}
