package com.lucky.task_manager.task.application.services;

import com.lucky.task_manager.task.application.dtos.SubTaskDTO;
import com.lucky.task_manager.task.application.dtos.SubTaskResponse;
import com.lucky.task_manager.task.application.exceptions.TaskAlreadyCompletedException;

import java.util.UUID;

public interface ICreateSubTaskService {
    SubTaskResponse execute(SubTaskDTO taskDTO, UUID taskId) throws TaskAlreadyCompletedException;
}
