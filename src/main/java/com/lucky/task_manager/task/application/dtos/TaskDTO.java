package com.lucky.task_manager.task.application.dtos;

import com.lucky.task_manager.task.domain.enums.Status;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDTO(@NotNull UUID id, @NotNull String title, String description, Status status, LocalDateTime createdAt, @NotNull UUID userId) {
}
