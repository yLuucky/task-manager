package com.lucky.task_manager.task.controllers;

import com.lucky.task_manager.task.application.dtos.TaskDTO;
import com.lucky.task_manager.task.application.dtos.TaskResponse;
import com.lucky.task_manager.task.application.exceptions.TaskHasOpenSubTasksException;
import com.lucky.task_manager.task.domain.enums.Status;
import com.lucky.task_manager.user.application.exceptions.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.lucky.task_manager.task.application.exceptions.ITaskExceptions.TASK_HAS_OPEN_SUBTASKS_EXCEPTION_MSG;
import static com.lucky.task_manager.user.application.exceptions.IUserExceptions.USER_NOT_FOUND_EXCEPTION_MSG;

@RequestMapping("/task-api")
public interface TaskAPI {

    @Operation(summary = "Create a new task", description = "Create a new task on our system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class))),
            @ApiResponse(responseCode = "400", description = USER_NOT_FOUND_EXCEPTION_MSG,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/tasks")
    ResponseEntity<Void> createTask(TaskDTO taskDTO) throws UserNotFoundException;

    @Operation(summary = "Retrieve a task", description = "Retrieve task by id and status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task successfully retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "404", description = "Task not found with this status",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/tasks")
    ResponseEntity<TaskResponse> retrieveTask(@PathParam("status") Status status, int page);

    @Operation(summary = "Retrieve a task", description = "Update Task Status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "400", description = TASK_HAS_OPEN_SUBTASKS_EXCEPTION_MSG,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/tasks/{id}/status")
    ResponseEntity<Void> updateTask(@PathParam("id") UUID taskId, @PathParam("status") Status status) throws TaskHasOpenSubTasksException;
}
