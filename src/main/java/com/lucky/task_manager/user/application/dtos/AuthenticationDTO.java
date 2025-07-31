package com.lucky.task_manager.user.application.dtos;

import jakarta.validation.constraints.NotNull;

public record AuthenticationDTO(@NotNull String email, @NotNull String password) {
}
