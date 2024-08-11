package com.mrodriguezdev.taskcli.exception;

public class TaskNotFoundException extends TaskOperationException {
    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
