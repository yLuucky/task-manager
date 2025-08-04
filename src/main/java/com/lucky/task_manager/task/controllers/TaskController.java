package com.lucky.task_manager.task.controllers;

import com.lucky.task_manager.task.application.dtos.TaskDTO;
import com.lucky.task_manager.task.application.dtos.TaskResponse;
import com.lucky.task_manager.task.application.exceptions.TaskHasOpenSubTasksException;
import com.lucky.task_manager.task.application.services.ICreateTaskService;
import com.lucky.task_manager.task.application.services.IRetrieveAllTasksByStatus;
import com.lucky.task_manager.task.application.services.IUpdateTaskService;
import com.lucky.task_manager.task.domain.enums.Status;
import com.lucky.task_manager.user.application.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TaskController implements TaskAPI{

    private final ICreateTaskService createTaskService;
    private final IUpdateTaskService updateTaskService;
    private final IRetrieveAllTasksByStatus retrieveTaskServiceBy;

    @Autowired
    public TaskController(final ICreateTaskService createTaskService,
                          final IUpdateTaskService updateTaskService,
                          final IRetrieveAllTasksByStatus retrieveTaskServiceBy) {
        this.createTaskService = createTaskService;
        this.updateTaskService = updateTaskService;
        this.retrieveTaskServiceBy = retrieveTaskServiceBy;
    }

    @Override
    public ResponseEntity<Void> createTask(TaskDTO taskDTO) throws UserNotFoundException {
        createTaskService.execute(taskDTO);

        return ResponseEntity.status(201).build();
    }

    @Override
    public ResponseEntity<TaskResponse> retrieveTask(final Status status, final int page) {
        retrieveTaskServiceBy.execute(status, page);

        return ResponseEntity.status(201).build();
    }

    @Override
    public ResponseEntity<Void> updateTask(UUID taskId, Status status) throws TaskHasOpenSubTasksException {
        updateTaskService.execute(taskId, status);

        return ResponseEntity.status(200).build();
    }

}
