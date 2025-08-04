package com.lucky.task_manager.task.application.services.Impl;

import com.lucky.task_manager.task.application.exceptions.TaskAlreadyCompletedException;
import com.lucky.task_manager.task.application.services.IUpdateSubTaskService;
import com.lucky.task_manager.task.domain.enums.Status;
import com.lucky.task_manager.task.domain.models.SubTask;
import com.lucky.task_manager.task.domain.models.Task;
import com.lucky.task_manager.task.domain.repositories.ISubTaskRepository;
import com.lucky.task_manager.task.domain.repositories.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.lucky.task_manager.task.application.exceptions.ITaskExceptions.TASK_ALREADY_COMPLETED_EXCEPTION;

@Service
public class UpdateSubTaskService implements IUpdateSubTaskService {

    private final ISubTaskRepository subTaskRepository;
    private final ITaskRepository ITaskRepository;

    @Autowired
    public UpdateSubTaskService(final ISubTaskRepository subTaskRepository,
                                final ITaskRepository iTaskRepository) {
        this.subTaskRepository = subTaskRepository;
        ITaskRepository = iTaskRepository;
    }

    @Override
    public void execute(final UUID taskId, final Status status) throws TaskAlreadyCompletedException {
        final SubTask subTask = subTaskRepository.findById(taskId).orElseThrow();
        final Task task = ITaskRepository.findById(subTask.getTaskId()).orElseThrow();

        if (task.getStatus().equals(Status.CLOSED)) {
            throw TASK_ALREADY_COMPLETED_EXCEPTION;
        }

        subTask.updateStatus(status);

        subTaskRepository.save(subTask);
    }
}
