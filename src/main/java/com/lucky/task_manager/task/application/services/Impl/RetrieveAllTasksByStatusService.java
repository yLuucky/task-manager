package com.lucky.task_manager.task.application.services.Impl;

import com.lucky.task_manager.task.application.dtos.TaskResponse;
import com.lucky.task_manager.task.application.services.IRetrieveAllTasksByStatusService;
import com.lucky.task_manager.task.domain.enums.Status;
import com.lucky.task_manager.task.domain.mappers.TaskMapper;
import com.lucky.task_manager.task.domain.models.Task;
import com.lucky.task_manager.task.domain.repositories.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetrieveAllTasksByStatusService implements IRetrieveAllTasksByStatusService {

    private final ITaskRepository ITaskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public RetrieveAllTasksByStatusService(final ITaskRepository ITaskRepository,
                                           final TaskMapper taskMapper) {
        this.ITaskRepository = ITaskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskResponse> execute(final Status status, final int page) {
        List<Task> tasks;
        tasks = ITaskRepository.findByStatus(status, Pageable.ofSize(page));

        return tasks.stream().map(taskMapper::toResponse).toList();
    }
}
