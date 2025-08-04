package com.lucky.task_manager.task.application.services;

import com.lucky.task_manager.task.application.exceptions.TaskHasOpenSubTasksException;
import com.lucky.task_manager.task.domain.enums.Status;

import java.util.UUID;

public interface IUpdateTaskService {
    void execute(UUID taskId, Status status) throws TaskHasOpenSubTasksException;
}
