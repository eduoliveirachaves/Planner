package com.edu.planner.entity;

import com.edu.planner.utils.Enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity owner;
    
    @Column(nullable = false, length = 100)
    private String title;
    
    @Column(length = 500)
    private String description;
    
    //if the user send
    @Column(name = "start_date")
    private LocalDate startDate;
    
    @Column(name = "due_date")
    private LocalDate dueDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;
    
    @Min(1)
    @Max(5)
    private Integer priority;
    
    private String category;
    
    //only if repeats??? like 2l (water) or 20 min or 20 km ?
    //dont know yet
    private String objective;
    
    
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    public Task () {
    }
    
    public Task (String title, String description, LocalDate dueDate, UserEntity user) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.owner = user;
    }
    
    public Task(String title, String description, LocalDate startDate, LocalDate dueDate, Status status, Integer priority, String category, String objective, UserEntity owner) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
        this.category = category;
        this.objective = objective;
        this.owner = owner;
    }
    
    public void setTitle (String title) {
        if (title != null) {
            this.title = title;
        }
    }
    
    public void setCategory (String category) {
        if (category != null) {
            this.category = category;
        }
    }
    
    public void setDescription (String description) {
        if (description != null) {
            this.description = description;
        }
    }
    
    public void setDueDate (LocalDate dueDate) {
        if (dueDate != null) {
            this.dueDate = dueDate;
        }
    }
    
    public void setStatus (Status status) {
        if (status != null) {
            this.status = status;
        }
    }
    
    public void setPriority (Integer priority) {
        if (priority != null) {
            this.priority = priority;
        }
    }
    
    public void setObjective (String objective) {
        if (objective != null) {
            this.objective = objective;
        }
    }
    
    public void setOwner (UserEntity owner) {
        this.owner = owner;
    }
    
    public void setStartDate (LocalDate startDate) {
        if (startDate != null) {
            this.startDate = startDate;
        }
    }
    
    @Override
    public String toString () {
        return "Task{" + "id=" + id + ", title='" + title + '\'' + ", description='" + description + '\'' + ", startDate=" + startDate + ", dueDate=" + dueDate + ", status=" + status + ", priority=" + priority + ", category='" + category + '\'' + ", score=" + objective + ", owner=" + owner + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
}
