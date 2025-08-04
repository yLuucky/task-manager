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
public class SubTask {

    @Id
    @GeneratedValue(generator = "UUID4")
    @UuidGenerator
    @Column(name = "sub_task_id", columnDefinition = "UUID")
    private UUID subTaskId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30)
    private Status status = Status.OPEN;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime concludedAt;

    @Column(name = "task_id", nullable = false)
    private UUID taskId;

    public void updateStatus(Status newStatus) {
        if (newStatus.equals(Status.CLOSED)) {
            this.completeTask();
            return;
        }
        this.status = newStatus;
        this.concludedAt = null;
    }

    private void completeTask() {
        this.status = Status.CLOSED;
        this.concludedAt = LocalDateTime.now();
    }

}
