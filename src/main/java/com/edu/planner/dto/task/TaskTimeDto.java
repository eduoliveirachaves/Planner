package com.edu.planner.dto.task;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalTime;

/**
 * This class represents a task time.
 * Used in the TaskController to send the task back.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TaskTimeDto(LocalTime startTime, LocalTime endTime) {

}
