package com.lucky.task_manager.task.application.exceptions;

public class TaskHasOpenSubTasksException extends Exception{
    public TaskHasOpenSubTasksException(String message) {
        super(message);
    }
}
