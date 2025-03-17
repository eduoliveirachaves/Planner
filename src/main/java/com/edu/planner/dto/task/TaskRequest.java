package com.edu.planner.dto.task;

import com.edu.planner.utils.Enums;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TaskRequest(
        String title,
        String description,
        Enums.TaskType type,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH-mm-ss") LocalDate startTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH-mm-ss") LocalDate endTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate dueDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate startDate,
        Enums.Status status,
        Integer priority,
        String category,
        String objective,
        List<TaskDayScheduleDto> schedule) {}
