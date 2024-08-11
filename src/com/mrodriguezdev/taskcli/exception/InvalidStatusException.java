package com.mrodriguezdev.taskcli.exception;

public class InvalidStatusException extends TaskOperationException {
    public InvalidStatusException(String message) {
        super(message);
    }

    public InvalidStatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
