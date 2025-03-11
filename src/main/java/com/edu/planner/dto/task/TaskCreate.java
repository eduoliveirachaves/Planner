package com.edu.planner.dto.task;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TaskCreate(
        @NotBlank String title,
        @Nullable String description,
        @NotNull LocalDate dueDate ) {}
