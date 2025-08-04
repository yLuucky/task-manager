package com.lucky.task_manager.task.application.services.Impl;

import com.lucky.task_manager.task.application.exceptions.TaskHasOpenSubTasksException;
import com.lucky.task_manager.task.application.services.IUpdateTaskService;
import com.lucky.task_manager.task.domain.enums.Status;
import com.lucky.task_manager.task.domain.models.Task;
import com.lucky.task_manager.task.domain.repositories.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateTaskService implements IUpdateTaskService {

    private final ITaskRepository ITaskRepository;

    @Autowired
    public UpdateTaskService(final ITaskRepository ITaskRepository) {
        this.ITaskRepository = ITaskRepository;
    }

    @Override
    public void execute(final UUID taskId, final Status status) throws TaskHasOpenSubTasksException {
        final Task task = ITaskRepository.findById(taskId).orElseThrow();
        task.updateStatus(status);

        ITaskRepository.save(task);
    }
}
