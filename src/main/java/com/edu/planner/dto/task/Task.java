package com.edu.planner.dto.task;

import java.time.LocalDate;

public class Task {

    private final String title;

    private final String description;

    private final LocalDate dueDate;

    private String status;

    private String owner;


    public Task(String title, String description, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }


    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description;
    }


    public LocalDate getDueDate() {
        return dueDate;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getOwner() {
        return owner;
    }


    public void setOwner(String owner) {
        this.owner = owner;
    }


    public enum Status {
        PENDING,
        COMPLETED
    }
}
