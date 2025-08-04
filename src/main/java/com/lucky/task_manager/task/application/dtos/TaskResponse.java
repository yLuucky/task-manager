package com.lucky.task_manager.task.application.dtos;

import com.lucky.task_manager.task.domain.enums.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TaskResponse(UUID taskId, String title, String description, Status status, LocalDateTime createdAt, LocalDateTime concludedAt, UUID userId, List<SubTaskResponse> subTasks) {}
