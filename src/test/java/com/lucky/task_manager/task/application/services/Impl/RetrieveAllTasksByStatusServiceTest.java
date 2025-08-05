package com.lucky.task_manager.task.application.services.Impl;

import com.lucky.task_manager.task.application.dtos.TaskResponse;
import com.lucky.task_manager.task.domain.enums.Status;
import com.lucky.task_manager.task.domain.mappers.TaskMapper;
import com.lucky.task_manager.task.domain.models.Task;
import com.lucky.task_manager.task.domain.repositories.ITaskRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RetrieveAllTasksByStatusServiceTest {

    @Mock
    private ITaskRepository ITaskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private RetrieveAllTasksByStatusService retrieveAllTasksByStatusService;

    @Test
    void testExecuteWithValidStatusAndPage() {
        Status status = Status.OPEN;
        int page = 5;
        Pageable pageable = PageRequest.ofSize(page);

        Task task = new Task();
        task.setTaskId(UUID.randomUUID());
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setStatus(Status.OPEN);
        task.setCreatedAt(LocalDateTime.now());
        task.setConcludedAt(null);
        task.setUserId(UUID.randomUUID());
        task.setSubTasks(Collections.emptyList());

        List<Task> taskList = List.of(task);
        Page<Task> taskPage = new PageImpl<>(taskList);
        TaskResponse taskResponse = new TaskResponse(task.getTaskId(), task.getTitle(), task.getDescription(),
                task.getStatus(), task.getCreatedAt(), task.getConcludedAt(), task.getUserId(), Collections.emptyList());

        when(ITaskRepository.findByStatus(status, pageable)).thenReturn(taskPage.getContent());
        when(taskMapper.toResponse(any(Task.class))).thenReturn(taskResponse);

        List<TaskResponse> result = retrieveAllTasksByStatusService.execute(status, page);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).title()).isEqualTo("Test Task");
        assertThat(result.get(0).status()).isEqualTo(Status.OPEN);

        verify(ITaskRepository, times(1)).findByStatus(status, pageable);
        verify(taskMapper, times(1)).toResponse(task);
    }

    @Test
    void testExecuteWithEmptyTasks() {
        Status status = Status.CLOSED;
        int page = 10;
        Pageable pageable = PageRequest.ofSize(page);

        when(ITaskRepository.findByStatus(status, pageable)).thenReturn(Collections.emptyList());

        List<TaskResponse> result = retrieveAllTasksByStatusService.execute(status, page);

        assertThat(result).isEmpty();

        verify(ITaskRepository, times(1)).findByStatus(status, pageable);
        verifyNoInteractions(taskMapper);
    }
}