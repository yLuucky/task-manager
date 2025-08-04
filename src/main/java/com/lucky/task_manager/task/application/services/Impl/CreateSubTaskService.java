package com.lucky.task_manager.task.application.services.Impl;

import com.lucky.task_manager.task.application.dtos.SubTaskDTO;
import com.lucky.task_manager.task.application.dtos.SubTaskResponse;
import com.lucky.task_manager.task.application.exceptions.TaskAlreadyCompletedException;
import com.lucky.task_manager.task.application.services.ICreateSubTaskService;
import com.lucky.task_manager.task.domain.enums.Status;
import com.lucky.task_manager.task.domain.mappers.SubTaskMapper;
import com.lucky.task_manager.task.domain.models.SubTask;
import com.lucky.task_manager.task.domain.models.Task;
import com.lucky.task_manager.task.domain.repositories.ISubTaskRepository;
import com.lucky.task_manager.task.domain.repositories.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.lucky.task_manager.task.application.exceptions.ITaskExceptions.TASK_ALREADY_COMPLETED_EXCEPTION;

@Service
public class CreateSubTaskService implements ICreateSubTaskService {

    private final ITaskRepository ITaskRepository;
    private final ISubTaskRepository ISubTaskRepository;
    private final SubTaskMapper subTaskMapper;

    @Autowired
    public CreateSubTaskService(final ITaskRepository ITaskRepository,
                                final ISubTaskRepository ISubTaskRepository,
                                final SubTaskMapper subTaskMapper) {
        this.ITaskRepository = ITaskRepository;
        this.ISubTaskRepository = ISubTaskRepository;
        this.subTaskMapper = subTaskMapper;
    }

    @Override
    public SubTaskResponse execute(SubTaskDTO subTaskDTO, final UUID taskId) throws TaskAlreadyCompletedException {
        final Task task = ITaskRepository.findById(taskId).orElseThrow();

        if (task.getStatus().equals(Status.CLOSED)) {
            throw TASK_ALREADY_COMPLETED_EXCEPTION;
        }

        SubTask subTask = new SubTask();
        subTask.setUserId(subTaskDTO.userId());
        subTask.setTaskId(taskId);
        subTask.setTitle(subTaskDTO.title());
        subTask.setDescription(subTaskDTO.description());
        subTask.setStatus(Status.OPEN);
        subTask.setCreatedAt(LocalDateTime.now());

        SubTask savedSubTask = ISubTaskRepository.save(subTask);
        return subTaskMapper.toResponse(savedSubTask);
    }
}
