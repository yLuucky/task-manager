package com.lucky.task_manager.user.application.services;

import com.lucky.task_manager.user.application.dtos.AuthenticationDTO;
import com.lucky.task_manager.user.application.exceptions.IncorrectLoginInformationException;

public interface ILoginUserService {
    String execute(AuthenticationDTO authenticationDTO) throws IncorrectLoginInformationException;
}
