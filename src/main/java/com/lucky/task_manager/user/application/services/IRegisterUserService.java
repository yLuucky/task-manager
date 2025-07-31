package com.lucky.task_manager.user.application.services;

import com.lucky.task_manager.user.application.dtos.RegisterUserDTO;
import com.lucky.task_manager.user.application.exceptions.UserAlreadyExistsWithEmailException;

public interface IRegisterUserService {
    void execute(RegisterUserDTO registerUserDTO) throws UserAlreadyExistsWithEmailException;
}
