package com.edu.planner.dto.task;

import java.time.LocalTime;

public record TaskTimeDto (LocalTime startTime,
                           LocalTime endTime){}
