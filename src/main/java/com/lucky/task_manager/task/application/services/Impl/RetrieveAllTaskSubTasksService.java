package com.lucky.task_manager.task.application.services.Impl;

import com.lucky.task_manager.task.application.dtos.SubTaskResponse;
import com.lucky.task_manager.task.application.services.IRetrieveAllTaskSubTasksService;
import com.lucky.task_manager.task.domain.mappers.SubTaskMapper;
import com.lucky.task_manager.task.domain.models.SubTask;
import com.lucky.task_manager.task.domain.repositories.ISubTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RetrieveAllTaskSubTasksService implements IRetrieveAllTaskSubTasksService {

    private final ISubTaskRepository subTaskRepository;
    private final SubTaskMapper subTaskMapper;

    @Autowired
    public RetrieveAllTaskSubTasksService(final ISubTaskRepository subTaskRepository,
                                          final SubTaskMapper subTaskMapper) {
        this.subTaskRepository = subTaskRepository;
        this.subTaskMapper = subTaskMapper;
    }

    @Override
    public List<SubTaskResponse> execute(final UUID taskId) {
        List<SubTask> taskResponses = subTaskRepository.findByTaskId(taskId);

        return subTaskMapper.toResponses(taskResponses);
    }
}
