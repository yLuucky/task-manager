package com.lucky.task_manager.task.application.services.Impl;

import com.lucky.task_manager.task.application.dtos.TaskDTO;
import com.lucky.task_manager.task.application.dtos.TaskResponse;
import com.lucky.task_manager.task.application.services.ICreateTaskService;
import com.lucky.task_manager.task.domain.enums.Status;
import com.lucky.task_manager.task.domain.mappers.TaskMapper;
import com.lucky.task_manager.task.domain.models.Task;
import com.lucky.task_manager.task.domain.repositories.ITaskRepository;
import com.lucky.task_manager.user.application.exceptions.UserNotFoundException;
import com.lucky.task_manager.user.domain.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.lucky.task_manager.user.application.exceptions.IUserExceptions.USER_NOT_FOUND_EXCEPTION;

@Service
public class CreateTaskService implements ICreateTaskService {

    private final IUserRepository userRepository;
    private final ITaskRepository ITaskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public CreateTaskService(final IUserRepository userRepository,
                             final ITaskRepository ITaskRepository,
                             final TaskMapper taskMapper) {
        this.userRepository = userRepository;
        this.ITaskRepository = ITaskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskResponse execute(TaskDTO taskDTO) throws UserNotFoundException {
        final boolean userExists = userRepository.existsById(taskDTO.userId());

        if (!userExists) {
            throw USER_NOT_FOUND_EXCEPTION;
        }

        Task task = new Task();
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setUserId(taskDTO.userId());
        task.setStatus(Status.OPEN);
        task.setCreatedAt(LocalDateTime.now());

        Task savedTask = ITaskRepository.save(task);
        return taskMapper.toResponse(savedTask);
    }
}
