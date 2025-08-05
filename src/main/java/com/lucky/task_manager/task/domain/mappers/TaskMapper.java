package com.lucky.task_manager.task.domain.mappers;

import com.lucky.task_manager.task.application.dtos.TaskResponse;
import com.lucky.task_manager.task.domain.models.Task;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskMapper {

    public static TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getTaskId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getConcludedAt(),
                task.getUserId(),
                task.getSubTasks() != null ? task.getSubTasks().stream().map(SubTaskMapper::toResponse).toList() : Collections.emptyList());
    }
}
