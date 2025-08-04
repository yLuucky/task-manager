package com.lucky.task_manager.task.domain.mappers;

import com.lucky.task_manager.task.application.dtos.SubTaskResponse;
import com.lucky.task_manager.task.domain.models.SubTask;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubTaskMapper {

    public SubTaskResponse toResponse(SubTask subTask) {
        return new SubTaskResponse(
                subTask.getSubTaskId(),
                subTask.getTitle(),
                subTask.getDescription(),
                subTask.getStatus(),
                subTask.getCreatedAt(),
                subTask.getConcludedAt(),
                subTask.getTaskId());
    }

    public List<SubTaskResponse> toResponses(List<SubTask> subTasks) {
        return subTasks.stream()
                .map(this::toResponse)
                .toList();
    }

}
