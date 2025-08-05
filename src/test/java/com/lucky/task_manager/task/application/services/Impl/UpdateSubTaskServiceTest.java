
package com.lucky.task_manager.task.application.services.Impl;

import com.lucky.task_manager.task.application.exceptions.TaskAlreadyCompletedException;
import com.lucky.task_manager.task.domain.enums.Status;
import com.lucky.task_manager.task.domain.models.SubTask;
import com.lucky.task_manager.task.domain.models.Task;
import com.lucky.task_manager.task.domain.repositories.ISubTaskRepository;
import com.lucky.task_manager.task.domain.repositories.ITaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateSubTaskServiceTest {

    @Mock
    private ISubTaskRepository subTaskRepository;

    @Mock
    private ITaskRepository taskRepository;

    @InjectMocks
    private UpdateSubTaskService updateSubTaskService;

    private SubTask subTask;
    private Task task;
    private UUID subTaskId;
    private UUID taskId;

    @BeforeEach
    void setUp() {
        subTaskId = UUID.randomUUID();
        taskId = UUID.randomUUID();

        subTask = new SubTask();
        subTask.setSubTaskId(subTaskId);
        subTask.setTaskId(taskId);
        subTask.setStatus(Status.OPEN);

        task = new Task();
        task.setTaskId(taskId);
        task.setStatus(Status.OPEN);
    }

    @Test
    @DisplayName("Should update subtask status successfully when main task is open")
    void shouldUpdateSubTaskStatusSuccessfully() {
        when(subTaskRepository.findById(subTaskId)).thenReturn(Optional.of(subTask));
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(subTaskRepository.save(any(SubTask.class))).thenReturn(subTask);

        assertDoesNotThrow(() ->
                updateSubTaskService.execute(subTaskId, Status.IN_PROGRESS)
        );

        verify(subTaskRepository).save(subTask);
        assertEquals(Status.IN_PROGRESS, subTask.getStatus());
    }

    @Test
    @DisplayName("Must throw exception when trying to update subtask of a closed task")
    void shouldThrowExceptionWhenParentTaskIsClosed() {
        task.setStatus(Status.CLOSED);

        when(subTaskRepository.findById(subTaskId)).thenReturn(Optional.of(subTask));
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        assertThrows(TaskAlreadyCompletedException.class, () ->
                updateSubTaskService.execute(subTaskId, Status.IN_PROGRESS)
        );

        verify(subTaskRepository, never()).save(any(SubTask.class));
    }

    @Test
    @DisplayName("Must update subtask to closed successfully")
    void shouldUpdateSubTaskStatusToClosedSuccessfully() {
        when(subTaskRepository.findById(subTaskId)).thenReturn(Optional.of(subTask));
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(subTaskRepository.save(any(SubTask.class))).thenReturn(subTask);

        assertDoesNotThrow(() ->
                updateSubTaskService.execute(subTaskId, Status.CLOSED)
        );

        verify(subTaskRepository).save(subTask);
        assertEquals(Status.CLOSED, subTask.getStatus());
    }

    @Test
    @DisplayName("Should throw exception when subtask not found")
    void shouldThrowExceptionWhenSubTaskNotFound() {
        when(subTaskRepository.findById(subTaskId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                updateSubTaskService.execute(subTaskId, Status.IN_PROGRESS)
        );

        verify(subTaskRepository, never()).save(any(SubTask.class));
    }

    @Test
    @DisplayName("Should throw exception when main task not found")
    void shouldThrowExceptionWhenParentTaskNotFound() {
        when(subTaskRepository.findById(subTaskId)).thenReturn(Optional.of(subTask));
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                updateSubTaskService.execute(subTaskId, Status.IN_PROGRESS)
        );

        verify(subTaskRepository, never()).save(any(SubTask.class));
    }
}