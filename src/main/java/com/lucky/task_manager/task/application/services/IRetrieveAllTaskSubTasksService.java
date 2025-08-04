package com.lucky.task_manager.task.application.services;

import com.lucky.task_manager.task.application.dtos.SubTaskResponse;

import java.util.List;
import java.util.UUID;

public interface IRetrieveAllTaskSubTasksService {
    List<SubTaskResponse> execute(UUID taskId);
}
