package com.lucky.task_manager.user.application.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UsersDTO(@NotNull UUID id) {
}
