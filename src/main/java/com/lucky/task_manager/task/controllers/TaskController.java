package com.lucky.task_manager.task.controllers;

import com.lucky.task_manager.task.application.dtos.SubTaskDTO;
import com.lucky.task_manager.task.application.dtos.SubTaskResponse;
import com.lucky.task_manager.task.application.dtos.TaskDTO;
import com.lucky.task_manager.task.application.dtos.TaskResponse;
import com.lucky.task_manager.task.application.exceptions.TaskAlreadyCompletedException;
import com.lucky.task_manager.task.application.exceptions.TaskHasOpenSubTasksException;
import com.lucky.task_manager.task.application.services.*;
import com.lucky.task_manager.task.domain.enums.Status;
import com.lucky.task_manager.task.domain.models.Task;
import com.lucky.task_manager.user.application.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class TaskController implements TaskAPI{

    private final ICreateTaskService createTaskService;
    private final IUpdateTaskService updateTaskService;
    private final IRetrieveAllTasksByStatusService retrieveTaskServiceBy;
    private final ICreateSubTaskService createSubTaskService;
    private final IUpdateSubTaskService updateSubTaskService;
    private final IRetrieveAllTaskSubTasksService retrieveAllTaskSubTasksService;

    @Autowired
    public TaskController(final ICreateTaskService createTaskService,
                          final IUpdateTaskService updateTaskService,
                          final IRetrieveAllTasksByStatusService retrieveTaskServiceBy,
                          final ICreateSubTaskService createSubTaskService,
                          final IUpdateSubTaskService updateSubTaskService,
                          final IRetrieveAllTaskSubTasksService retrieveAllTaskSubTasksService) {
        this.createTaskService = createTaskService;
        this.updateTaskService = updateTaskService;
        this.retrieveTaskServiceBy = retrieveTaskServiceBy;
        this.createSubTaskService = createSubTaskService;
        this.updateSubTaskService = updateSubTaskService;
        this.retrieveAllTaskSubTasksService = retrieveAllTaskSubTasksService;
    }

    @Override
    public ResponseEntity<TaskResponse> createTask(TaskDTO taskDTO) throws UserNotFoundException {
        TaskResponse taskResponse = createTaskService.execute(taskDTO);

        return ResponseEntity.status(201).body(taskResponse);
    }

    @Override
    public ResponseEntity<List<TaskResponse>> retrieveTask(final Status status, final int page) {
        List<TaskResponse> taskResponse = retrieveTaskServiceBy.execute(status, page);

        return ResponseEntity.status(200).body(taskResponse);
    }

    @Override
    public ResponseEntity<Void> updateTask(final UUID taskId, final Status status) throws TaskHasOpenSubTasksException {
        updateTaskService.execute(taskId, status);

        return ResponseEntity.status(200).build();
    }

    @Override
    public ResponseEntity<SubTaskResponse> createSubTask(final UUID taskId, SubTaskDTO subTaskDTO) throws TaskAlreadyCompletedException {
        SubTaskResponse subTaskResponse = createSubTaskService.execute(subTaskDTO, taskId);

        return ResponseEntity.status(201).body(subTaskResponse);
    }

    @Override
    public ResponseEntity<List<SubTaskResponse>> retrieveAllTaskSubtasks(final UUID taskId) {
        List<SubTaskResponse> taskResponses = retrieveAllTaskSubTasksService.execute(taskId);

        return ResponseEntity.status(200).body(taskResponses);
    }

    @Override
    public ResponseEntity<Void> updateSubTask(final UUID taskId, final Status status) throws TaskAlreadyCompletedException {
        updateSubTaskService.execute(taskId, status);

        return ResponseEntity.status(200).build();
    }

}
