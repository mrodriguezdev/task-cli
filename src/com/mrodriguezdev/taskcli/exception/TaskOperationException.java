package com.mrodriguezdev.taskcli.exception;

public class TaskOperationException extends RuntimeException {
    public TaskOperationException(String message) {
        super(message);
    }

    public TaskOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
