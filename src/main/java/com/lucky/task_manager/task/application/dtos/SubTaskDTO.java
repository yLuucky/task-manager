package com.lucky.task_manager.task.application.dtos;

import com.lucky.task_manager.task.domain.enums.Status;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record SubTaskDTO(@NotNull UUID id, @NotNull String title, String description, Status status, LocalDateTime createdAt, UUID userId, @NotNull UUID taskId) {
}
