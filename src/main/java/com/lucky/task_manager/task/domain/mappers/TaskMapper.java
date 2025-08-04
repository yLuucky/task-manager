package com.lucky.task_manager.task.domain.mappers;

import com.lucky.task_manager.task.application.dtos.TaskResponse;
import com.lucky.task_manager.task.domain.models.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final SubTaskMapper subTaskMapper;

    public TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getTaskId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getConcludedAt(),
                task.getUserId(),
                task.getSubTasks() != null ? task.getSubTasks().stream().map(subTaskMapper::toResponse).toList() : Collections.emptyList());
    }
}
