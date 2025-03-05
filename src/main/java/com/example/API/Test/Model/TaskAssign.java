package com.example.API.Test.Model;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks_assign", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"assigned_username", "task_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskAssign {
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "deadline_at")
    private LocalDateTime deadlineAt;

    @Column(name = "status_code", nullable = false)
    private String statusCode = "01"; // Default value

    @Column(name = "status", nullable = false)
    private String status = "Assigned"; // Default value

    @Column(name = "assigned_username")
    private String assignedUsername;

//    @Column(name = "assigned_emp_id")
//    private UUID assignedEmpId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_emp_id", foreignKey = @ForeignKey(name = "fk_tasks_assign_emp_id"))
    private Employee assignedEmployee;

    @CreationTimestamp
    @Column(name = "assigned_at", updatable = false)
    private LocalDateTime assignedAt;

    @Column(name = "assigned_by_username")
    private String assignedByUsername;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tasks_assign"))
    private Task task;
}
