package com.lucky.task_manager.task.application.exceptions;

import com.lucky.task_manager.user.application.exceptions.UserNotFoundException;

public interface ITaskExceptions {

    String TASK_ALREADY_COMPLETED_EXCEPTION_MSG = "Task is closed";
    TaskAlreadyCompletedException TASK_ALREADY_COMPLETED_EXCEPTION = new TaskAlreadyCompletedException(TASK_ALREADY_COMPLETED_EXCEPTION_MSG);

    String TASK_HAS_OPEN_SUBTASKS_EXCEPTION_MSG = "Is not possible to close this task because has open subtasks";
    TaskHasOpenSubTasksException TASK_HAS_OPEN_SUBTASKS_EXCEPTION = new TaskHasOpenSubTasksException(TASK_HAS_OPEN_SUBTASKS_EXCEPTION_MSG);

    String SUBTASK_NOT_FOUND_EXCEPTION_MSG = "Subtask not found";
    SubTaskNotFoundException SUBTASK_NOT_FOUND_EXCEPTION = new SubTaskNotFoundException(SUBTASK_NOT_FOUND_EXCEPTION_MSG);
}
