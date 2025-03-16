package com.edu.planner.dto.task;

import com.edu.planner.utils.Enums;

import java.util.List;

public record TaskScheduleDto(
        Enums.WeekDay day,
        List<TaskTimeDto> scheduledTimes) {}

// uses to create times and days for task repetitions  // to be implemented in the future