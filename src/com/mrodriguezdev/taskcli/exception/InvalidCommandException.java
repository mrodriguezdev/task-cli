package com.mrodriguezdev.taskcli.exception;

public class InvalidCommandException extends TaskOperationException {
    public InvalidCommandException(String message) {
        super(message);
    }

    public InvalidCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
