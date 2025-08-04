package com.lucky.task_manager.task.application.dtos;

import com.lucky.task_manager.task.domain.enums.Status;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record SubTaskDTO(@NotNull String title, String description, UUID userId) {
}
