package com.lucky.task_manager.user.application.exceptions;

public class UserAlreadyExistsWithEmailException extends Exception{

    public UserAlreadyExistsWithEmailException(String message) {
        super(message);
    }

}
