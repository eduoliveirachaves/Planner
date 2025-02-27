package com.edu.planner.dto.task;

import com.edu.planner.utils.Enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * This class represents a task.
 * Used in the TaskController to create, update, and retrieve tasks.
 */


@Getter
@Setter
public class Task {

    private final String title;

    private final String description;

    private final LocalDate dueDate;

    private Status status;

    private String owner;


    public Task(String title, String description, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }
}
