package com.lucky.task_manager.task.application.services;

import com.lucky.task_manager.task.application.dtos.TaskDTO;
import com.lucky.task_manager.task.application.dtos.TaskResponse;
import com.lucky.task_manager.user.application.exceptions.UserNotFoundException;

public interface ICreateTaskService {
    TaskResponse execute(TaskDTO taskDTO) throws UserNotFoundException;
}
