package com.lucky.task_manager.task.domain.models;

import com.lucky.task_manager.task.domain.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "sub_tasks")
public class SubTask extends Task {

    @Id
    @GeneratedValue(generator = "UUID4")
    @UuidGenerator
    private UUID subTaskId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.OPEN;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime concludedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    public void updateStatus(Status newStatus) {
        this.status = newStatus;
        if (newStatus.equals(Status.CLOSED)) {
            this.concludedAt = LocalDateTime.now();
        } else {
            this.concludedAt = null;
        }
    }

    public UUID getTaskId() {
        return task != null ? task.getTaskId() : null;
    }
}
