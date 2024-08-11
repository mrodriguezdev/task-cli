package com.mrodriguezdev.taskcli.model;

import com.mrodriguezdev.taskcli.exception.InvalidStatusException;

import java.util.Arrays;

public enum Status {
    TODO("todo"),
    IN_PROGRESS("in-progress"),
    DONE("done");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String get() {
        return this.status;
    }

    public static Status fromString(String statusString) {
        return Arrays.stream(values())
                .filter(status -> status.get().equalsIgnoreCase(statusString))
                .findFirst()
                .orElseThrow(() -> new InvalidStatusException(String.format("El estado '%s' es inválido. Por favor, proporcione un estado válido.", statusString)));
    }
}
