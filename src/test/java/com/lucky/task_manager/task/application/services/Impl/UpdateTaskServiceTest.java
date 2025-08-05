
package com.lucky.task_manager.task.application.services.Impl;

import com.lucky.task_manager.task.application.exceptions.TaskHasOpenSubTasksException;
import com.lucky.task_manager.task.domain.enums.Status;
import com.lucky.task_manager.task.domain.models.SubTask;
import com.lucky.task_manager.task.domain.models.Task;
import com.lucky.task_manager.task.domain.repositories.ITaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateTaskServiceTest {

    @Mock
    private ITaskRepository taskRepository;

    @InjectMocks
    private UpdateTaskService updateTaskService;

    private Task task;
    private UUID taskId;

    @BeforeEach
    void setUp() {
        taskId = UUID.randomUUID();
        task = new Task();
        task.setTaskId(taskId);
        task.setStatus(Status.OPEN);
        task.setSubTasks(new ArrayList<>());
    }

    @Test
    @DisplayName("It should update the status of the task to IN_STANDING successfully")
    void shouldUpdateTaskStatusToInProgressSuccessfully() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        assertDoesNotThrow(() -> updateTaskService.execute(taskId, Status.IN_PROGRESS));

        verify(taskRepository).save(task);
        assertEquals(Status.IN_PROGRESS, task.getStatus());
        assertNull(task.getConcludedAt());
    }

    @Test
    @DisplayName("It should update the task status to CLOSED when there are no subtasks")
    void shouldUpdateTaskStatusToClosedWhenNoSubTasks() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        assertDoesNotThrow(() -> updateTaskService.execute(taskId, Status.CLOSED));

        verify(taskRepository).save(task);
        assertEquals(Status.CLOSED, task.getStatus());
        assertNotNull(task.getConcludedAt());
    }

    @Test
    @DisplayName("Should throw exception when trying to close task with open subtasks")
    void shouldThrowExceptionWhenClosingTaskWithOpenSubTasks() {
        SubTask openSubTask = new SubTask();
        openSubTask.setStatus(Status.OPEN);
        task.getSubTasks().add(openSubTask);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        assertThrows(TaskHasOpenSubTasksException.class,
                () -> updateTaskService.execute(taskId, Status.CLOSED));

        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("It should allow you to close a task when all subtasks are closed")
    void shouldAllowClosingTaskWhenAllSubTasksAreClosed() {
        SubTask closedSubTask = new SubTask();
        closedSubTask.setStatus(Status.CLOSED);
        task.getSubTasks().add(closedSubTask);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        assertDoesNotThrow(() -> updateTaskService.execute(taskId, Status.CLOSED));

        verify(taskRepository).save(task);
        assertEquals(Status.CLOSED, task.getStatus());
        assertNotNull(task.getConcludedAt());
    }
}