package com.lucky.task_manager.user.application.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UsersResponseDTO(@NotNull UUID id, @NotNull String name, @NotNull String email) {
}
