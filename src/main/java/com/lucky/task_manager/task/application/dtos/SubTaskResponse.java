package com.lucky.task_manager.task.application.dtos;

import com.lucky.task_manager.task.domain.enums.Status;

import java.time.LocalDateTime;
import java.util.UUID;

public record SubTaskResponse(UUID subTaskId, String title, String description, Status status, LocalDateTime createdAt, LocalDateTime concludedAt, UUID taskId) {
}
