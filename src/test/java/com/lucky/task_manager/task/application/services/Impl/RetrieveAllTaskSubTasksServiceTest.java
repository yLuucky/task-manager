package com.lucky.task_manager.task.application.services.Impl;

import com.lucky.task_manager.task.application.dtos.SubTaskResponse;
import com.lucky.task_manager.task.domain.enums.Status;
import com.lucky.task_manager.task.domain.mappers.SubTaskMapper;
import com.lucky.task_manager.task.domain.models.SubTask;
import com.lucky.task_manager.task.domain.repositories.ISubTaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class RetrieveAllTaskSubTasksServiceTest {

    @Mock
    private ISubTaskRepository subTaskRepository;

    @Mock
    private SubTaskMapper subTaskMapper;

    @InjectMocks
    private RetrieveAllTaskSubTasksService service;

    public RetrieveAllTaskSubTasksServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_WithValidTaskId_ReturnsMappedSubTaskResponses() {
        UUID taskId = UUID.randomUUID();

        List<SubTask> subTasks = new ArrayList<>();

        SubTask subTask1 = new SubTask();
        subTask1.setSubTaskId(UUID.randomUUID());
        subTask1.setTitle("SubTask 1");
        subTask1.setDescription("Description 1");
        subTask1.setStatus(Status.IN_PROGRESS);
        subTask1.setCreatedAt(LocalDateTime.now());
        subTask1.setConcludedAt(null);
        subTask1.setTaskId(taskId);

        SubTask subTask2 = new SubTask();

        subTask2.setSubTaskId(UUID.randomUUID());
        subTask2.setTitle("SubTask 2");
        subTask2.setDescription("Description 2");
        subTask2.setStatus(Status.IN_PROGRESS);
        subTask2.setCreatedAt(LocalDateTime.now().minusDays(1));
        subTask2.setConcludedAt(LocalDateTime.now());
        subTask2.setTaskId(taskId);

        subTasks.add(subTask1);
        subTasks.add(subTask2);

        when(subTaskRepository.findByTaskId(taskId)).thenReturn(subTasks);

        List<SubTaskResponse> responses = new ArrayList<>();
        SubTaskResponse response1 = new SubTaskResponse(
                subTask1.getSubTaskId(), subTask1.getTitle(), subTask1.getDescription(),
                subTask1.getStatus(), subTask1.getCreatedAt(), subTask1.getConcludedAt(), taskId
        );
        SubTaskResponse response2 = new SubTaskResponse(
                subTask2.getSubTaskId(), subTask2.getTitle(), subTask2.getDescription(),
                subTask2.getStatus(), subTask2.getCreatedAt(), subTask2.getConcludedAt(), taskId
        );
        responses.add(response1);
        responses.add(response2);

        when(subTaskMapper.toResponses(subTasks)).thenReturn(responses);

        List<SubTaskResponse> result = service.execute(taskId);

        assertEquals(2, result.size());
        assertEquals(response1, result.get(0));
        assertEquals(response2, result.get(1));
    }

    @Test
    void execute_WithNoSubTasks_ReturnsEmptyList() {
        UUID taskId = UUID.randomUUID();

        when(subTaskRepository.findByTaskId(taskId)).thenReturn(new ArrayList<>());
        when(subTaskMapper.toResponses(new ArrayList<>())).thenReturn(new ArrayList<>());

        List<SubTaskResponse> result = service.execute(taskId);

        assertTrue(result.isEmpty());
    }
}