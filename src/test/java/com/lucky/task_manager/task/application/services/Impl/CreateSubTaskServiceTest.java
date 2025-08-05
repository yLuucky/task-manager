package com.lucky.task_manager.task.application.services.Impl;

import com.lucky.task_manager.task.application.dtos.SubTaskDTO;
import com.lucky.task_manager.task.application.dtos.SubTaskResponse;
import com.lucky.task_manager.task.application.exceptions.TaskAlreadyCompletedException;
import com.lucky.task_manager.task.domain.enums.Status;
import com.lucky.task_manager.task.domain.mappers.SubTaskMapper;
import com.lucky.task_manager.task.domain.models.SubTask;
import com.lucky.task_manager.task.domain.models.Task;
import com.lucky.task_manager.task.domain.repositories.ISubTaskRepository;
import com.lucky.task_manager.task.domain.repositories.ITaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateSubTaskServiceTest {

    @Mock
    private ITaskRepository taskRepository;

    @Mock
    private ISubTaskRepository subTaskRepository;

    @Mock
    private SubTaskMapper subTaskMapper;

    @InjectMocks
    private CreateSubTaskService createSubTaskService;

    public CreateSubTaskServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateSubTaskWhenTaskIsOpen() throws TaskAlreadyCompletedException {
        UUID taskId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Task task = new Task();
        task.setTaskId(taskId);
        task.setStatus(Status.OPEN);

        SubTaskDTO subTaskDTO = new SubTaskDTO("Subtask Title", "Subtask Description", userId);
        SubTask subTask = new SubTask();
        subTask.setTaskId(taskId);
        subTask.setUserId(userId);
        subTask.setTitle(subTaskDTO.title());
        subTask.setDescription(subTaskDTO.description());
        subTask.setStatus(Status.OPEN);
        subTask.setCreatedAt(LocalDateTime.now());

        SubTask savedSubTask = new SubTask();
        savedSubTask.setTaskId(taskId);
        savedSubTask.setUserId(userId);
        savedSubTask.setTitle(subTaskDTO.title());
        savedSubTask.setDescription(subTaskDTO.description());
        savedSubTask.setStatus(Status.OPEN);
        savedSubTask.setCreatedAt(LocalDateTime.now());
        savedSubTask.setSubTaskId(UUID.randomUUID());

        SubTaskResponse subTaskResponse = new SubTaskResponse(
                savedSubTask.getSubTaskId (),
                savedSubTask.getTitle(),
                savedSubTask.getDescription(),
                savedSubTask.getStatus(),
                savedSubTask.getCreatedAt(),
                null,
                taskId
        );

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(subTaskRepository.save(any(SubTask.class))).thenReturn(savedSubTask);
        when(subTaskMapper.toResponse(savedSubTask)).thenReturn(subTaskResponse);

        SubTaskResponse response = createSubTaskService.execute(subTaskDTO, taskId);

        assertNotNull(response);
        assertEquals(subTaskDTO.title(), response.title());
        assertEquals(subTaskDTO.description(), response.description());
        assertEquals(Status.OPEN, response.status());
        verify(taskRepository, times(1)).findById(taskId);
        verify(subTaskRepository, times(1)).save(any(SubTask.class));
        verify(subTaskMapper, times(1)).toResponse(savedSubTask);
    }

    @Test
    void shouldThrowExceptionWhenTaskIsClosed() {
        UUID taskId = UUID.randomUUID();
        Task task = new Task();
        task.setTaskId(taskId);
        task.setStatus(Status.CLOSED);

        SubTaskDTO subTaskDTO = new SubTaskDTO("Subtask Title", "Subtask Description", UUID.randomUUID());

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        TaskAlreadyCompletedException exception = assertThrows(TaskAlreadyCompletedException.class, () -> {
            createSubTaskService.execute(subTaskDTO, taskId);
        });

        assertEquals("Task is closed", exception.getMessage());
        verify(taskRepository, times(1)).findById(taskId);
        verifyNoInteractions(subTaskRepository);
        verifyNoInteractions(subTaskMapper);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        UUID taskId = UUID.randomUUID();
        SubTaskDTO subTaskDTO = new SubTaskDTO("Subtask Title", "Subtask Description", UUID.randomUUID());

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            createSubTaskService.execute(subTaskDTO, taskId);
        });

        verify(taskRepository, times(1)).findById(taskId);
        verifyNoInteractions(subTaskRepository);
        verifyNoInteractions(subTaskMapper);
    }
}