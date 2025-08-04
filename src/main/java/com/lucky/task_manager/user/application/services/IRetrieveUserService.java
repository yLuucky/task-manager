package com.lucky.task_manager.user.application.services;

import com.lucky.task_manager.user.application.dtos.UsersResponseDTO;
import com.lucky.task_manager.user.application.exceptions.UserNotFoundException;

import java.util.UUID;

public interface IRetrieveUserService {
    UsersResponseDTO execute(UUID id) throws UserNotFoundException;
}
