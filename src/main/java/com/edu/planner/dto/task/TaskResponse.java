package com.edu.planner.dto.task;

import com.edu.planner.utils.Enums;
import com.edu.planner.utils.Enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.List;

/**
 * This class represents a task response.
 * Used in the TaskController to send the task back.
 */


@JsonInclude(JsonInclude.Include.NON_NULL)
public record TaskResponse(
        Long id,
        String title,
        String description,
        Enums.TaskType type,
        LocalDate dueDate,
        Status status,
        List<TaskDayScheduleDto> schedule) {
    
}
