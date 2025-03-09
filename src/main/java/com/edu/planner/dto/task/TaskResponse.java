package com.edu.planner.dto.task;

import com.edu.planner.utils.Enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * OLD DESCRIPTION
 * This class represents a task.
 * Used in the TaskController to create, update, and retrieve tasks.
 */


@AllArgsConstructor
@Getter
@Setter
public class TaskResponse {

    private String title;

    private String description;

    private LocalDate dueDate;

    private Status status;

    private String owner;


    public TaskResponse(String title, String description, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }
}
