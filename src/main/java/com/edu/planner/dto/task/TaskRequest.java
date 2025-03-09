package com.edu.planner.dto.task;

import com.edu.planner.utils.Enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class TaskRequest {


    private final String title;

    private final String description;

    private final String dueDate;

    private Enums.Status status;


    //basicaly for create the task
    public TaskRequest(String t, String d, String date) {
        this.title = t;
        this.description = d;
        this.dueDate = date;
    }




    public LocalDate getDate() {
        return LocalDate.parse(dueDate);
    }
}
