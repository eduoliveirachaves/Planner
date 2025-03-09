package com.edu.planner.dto.task;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskRequest {

    private final String title;

    private final String description;

    private final String dueDate;


    public TaskRequest(String t, String d, String date) {
        this.title = t;
        this.description = d;
        this.dueDate = date;
    }


    public LocalDate getDate() {
        return LocalDate.parse(dueDate);
    }
}
