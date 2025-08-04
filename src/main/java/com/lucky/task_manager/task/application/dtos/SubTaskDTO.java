package com.lucky.task_manager.task.application.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SubTaskDTO(@NotNull String title, String description, UUID userId) {
}
