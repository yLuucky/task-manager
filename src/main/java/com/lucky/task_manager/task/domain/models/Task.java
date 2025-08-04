package com.lucky.task_manager.task.domain.models;

import com.lucky.task_manager.task.application.exceptions.TaskHasOpenSubTasksException;
import com.lucky.task_manager.task.domain.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.lucky.task_manager.task.application.exceptions.ITaskExceptions.TASK_HAS_OPEN_SUBTASKS_EXCEPTION;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(generator = "UUID4")
    @UuidGenerator
    private UUID taskId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Status status = Status.OPEN;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime concludedAt;

    @Column(nullable = false)
    private UUID userId;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SubTask> subTasks = new ArrayList<>();

    public void updateStatus(Status newStatus) throws TaskHasOpenSubTasksException {
        if (newStatus.equals(Status.CLOSED)) {
            this.completeTask();
            return;
        }
        this.status = newStatus;
        this.concludedAt = null;
    }

    private boolean canBeCompleted(){
        return subTasks.isEmpty() || subTasks.stream().allMatch(subTask -> subTask.getStatus().equals(Status.CLOSED));
    }

    private void completeTask() throws TaskHasOpenSubTasksException {
        if (!canBeCompleted()) throw TASK_HAS_OPEN_SUBTASKS_EXCEPTION;
        this.status = Status.CLOSED;
        this.concludedAt = LocalDateTime.now();
    }
}
