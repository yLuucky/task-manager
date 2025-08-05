package com.lucky.task_manager.task.domain.mappers;

import com.lucky.task_manager.task.application.dtos.SubTaskResponse;
import com.lucky.task_manager.task.domain.models.SubTask;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubTaskMapper {

    public static SubTaskResponse toResponse(SubTask subTask) {
        return new SubTaskResponse(
                subTask.getSubTaskId(),
                subTask.getTitle(),
                subTask.getDescription(),
                subTask.getStatus(),
                subTask.getCreatedAt(),
                subTask.getConcludedAt(),
                subTask.getTaskId());
    }

    public static List<SubTaskResponse> toResponses(List<SubTask> subTasks) {
        return subTasks.stream()
                .map(SubTaskMapper::toResponse)
                .toList();
    }

}
