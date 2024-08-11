package com.mrodriguezdev.taskcli.validator;

import com.mrodriguezdev.taskcli.exception.TaskArgumentException;
import com.mrodriguezdev.taskcli.model.Status;

public class TaskValidator {

    public static void validateAddArgs(String[] args) {
        if (args.length < 2) {
            throw new TaskArgumentException("Por favor, proporcione una descripción de la tarea para agregar.");
        }
    }

    public static void validateUpdateArgs(String[] args) {
        if (args.length < 3) {
            throw new TaskArgumentException("Por favor, proporcione una descripción de la tarea para actualizar y un ID.");
        }
        parseLongId(args[1]);
    }

    public static void validateMarkStatusArgs(String[] args) {
        if (args.length < 2) {
            throw new TaskArgumentException("Por favor, proporcione un ID de tarea para modificar.");
        }
        parseLongId(args[1]);
    }

    public static void validateDeleteArgs(String[] args) {
        if (args.length < 2) {
            throw new TaskArgumentException("Por favor, proporcione un ID de tarea para eliminar.");
        }
        parseLongId(args[1]);
    }

    public static void validateListArgs(String[] args) {
        if (args.length > 2) {
            throw new TaskArgumentException("Demasiados argumentos proporcionados para el comando 'list'.");
        }
    }

    public static long parseLongId(String idString) {
        try {
            return Long.parseLong(idString);
        } catch (NumberFormatException e) {
            throw new TaskArgumentException("Formato de ID inválido. Por favor, proporcione un ID numérico válido.");
        }
    }

    public static Status parseStatus(String statusString) {
        return Status.fromString(statusString);
    }
}

