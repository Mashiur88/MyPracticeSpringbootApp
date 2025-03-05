package com.example.API.Test.Model;

import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tasks", uniqueConstraints = {
        @UniqueConstraint(columnNames = "task_code"),
        @UniqueConstraint(columnNames = "task_name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "Task code is required")
    @Column(name = "task_code", unique = true)
    private String taskCode;

    @NotBlank(message = "Task name is required")
    @Size(max = 255, message = "Task name should not exceed 255 characters")
    @Column(name = "task_name", unique = true)
    private String taskName;
    
    @Column(name = "task_description")
    private String taskDescription;
    
    @Column(name = "status_code")
    private String statusCode = "01"; // Default value

    @Column(name = "status")
    private String status = "Pending"; // Default value
    
    @Column(name = "is_completed")
    private boolean isCompleted; // Default value
    
    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "created_by_username")
    private String createdByUsername;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by_username", length = 100)
    private String updatedByUsername;

    @Column(name = "approved_by_username")
    private String approvedByUsername;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;
}
