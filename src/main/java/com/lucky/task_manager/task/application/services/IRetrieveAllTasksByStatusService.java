package com.lucky.task_manager.task.application.services;

import com.lucky.task_manager.task.application.dtos.TaskResponse;
import com.lucky.task_manager.task.domain.enums.Status;

import java.util.List;

public interface IRetrieveAllTasksByStatusService {
    List<TaskResponse> execute(Status status, int page);
}
