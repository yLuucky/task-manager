package com.lucky.task_manager.task.application.exceptions;

public class TaskAlreadyCompletedException extends Exception{
    public TaskAlreadyCompletedException(String message) {
        super(message);
    }
}
