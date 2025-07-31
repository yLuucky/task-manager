package com.lucky.task_manager.task.domain.models;

import com.lucky.task_manager.task.domain.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Task {

    @Id
    private UUID taskId;
    private String title;
    private String description;
    private Status status;
    private Date createdAt;
    private Date concludedAt;
    private UUID userId;
}
