package com.edu.planner.dto.task;

import com.edu.planner.utils.Enums;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record TaskRequest(
        String title,
        String description,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate dueDate,
        @Nullable Enums.Status status) {
    
    
}
