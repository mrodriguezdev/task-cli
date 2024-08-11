package com.mrodriguezdev.taskcli.exception;

public class TaskArgumentException extends TaskOperationException {
    public TaskArgumentException(String message) {
        super(message);
    }

    public TaskArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
