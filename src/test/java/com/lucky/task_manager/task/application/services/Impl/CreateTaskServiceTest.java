package com.lucky.task_manager.task.application.services.Impl;

import com.lucky.task_manager.task.application.dtos.TaskDTO;
import com.lucky.task_manager.task.application.dtos.TaskResponse;
import com.lucky.task_manager.task.domain.enums.Status;
import com.lucky.task_manager.task.domain.models.Task;
import com.lucky.task_manager.task.domain.repositories.ITaskRepository;
import com.lucky.task_manager.user.application.exceptions.UserNotFoundException;
import com.lucky.task_manager.user.domain.repositories.IUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateTaskServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private ITaskRepository taskRepository;

    @InjectMocks
    private CreateTaskService createTaskService;

    public CreateTaskServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ShouldCreateTaskSuccessfullyWhenUserExists() throws UserNotFoundException {
        UUID userId = UUID.randomUUID();
        TaskDTO taskDTO = new TaskDTO("Test Title", "Test Description", userId);
        Task task = new Task();
        task.setTaskId(UUID.randomUUID());
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setUserId(userId);
        task.setStatus(Status.OPEN);
        task.setCreatedAt(LocalDateTime.now());

        when(userRepository.existsById(userId)).thenReturn(true);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponse response = createTaskService.execute(taskDTO);

        assertNotNull(response);
        assertEquals(task.getTaskId(), response.taskId());
        assertEquals(task.getTitle(), response.title());
        assertEquals(task.getDescription(), response.description());
        assertEquals(task.getStatus(), response.status());
        assertEquals(task.getCreatedAt(), response.createdAt());
        assertEquals(task.getUserId(), response.userId());

        verify(userRepository, times(1)).existsById(userId);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void ShouldThrowUserNotFoundExceptionWhenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();
        TaskDTO taskDTO = new TaskDTO("Test Title", "Test Description", userId);

        when(userRepository.existsById(userId)).thenReturn(false);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> createTaskService.execute(taskDTO));

        assertEquals("User with this Id was not found", exception.getMessage());
        verify(userRepository, times(1)).existsById(userId);
        verifyNoInteractions(taskRepository);
    }
}