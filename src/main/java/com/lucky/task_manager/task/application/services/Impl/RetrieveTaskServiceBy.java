package com.lucky.task_manager.task.application.services.Impl;

import com.lucky.task_manager.task.application.dtos.TaskResponse;
import com.lucky.task_manager.task.application.services.IRetrieveTaskServiceBy;
import com.lucky.task_manager.task.domain.enums.Status;
import com.lucky.task_manager.task.domain.mappers.TaskMapper;
import com.lucky.task_manager.task.domain.models.Task;
import com.lucky.task_manager.task.domain.repositories.ITaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RetrieveTaskServiceBy implements IRetrieveTaskServiceBy {

    private final ITaskRepository ITaskRepository;
    private final TaskMapper taskMapper;

    public RetrieveTaskServiceBy(final ITaskRepository ITaskRepository,
                                 final TaskMapper taskMapper) {
        this.ITaskRepository = ITaskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskResponse> execute(final UUID userId, final Status status) {
        List<Task> tasks;
        if (status != null) {
            tasks = ITaskRepository.findByUserIdAndStatus(userId, status);
        } else {
            tasks = ITaskRepository.findByUserId(userId);
        }

        return tasks.stream().map(taskMapper::toResponse).toList();
    }
}
