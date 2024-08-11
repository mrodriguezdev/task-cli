package com.mrodriguezdev.taskcli;

import com.mrodriguezdev.taskcli.command.Command;
import com.mrodriguezdev.taskcli.command.CommandImpl;
import com.mrodriguezdev.taskcli.exception.InvalidCommandException;
import com.mrodriguezdev.taskcli.exception.TaskOperationException;
import com.mrodriguezdev.taskcli.model.Status;
import com.mrodriguezdev.taskcli.model.Task;
import com.mrodriguezdev.taskcli.validator.TaskValidator;

public class TaskCli {
    private static final Command command = new CommandImpl();

    public static void main(String[] args) {
        if (args.length == 0) {
            showError("Por favor, proporcione argumentos o parámetros para ejecutar una acción.");
            return;
        }

        String commandName = args[0].toLowerCase();
        try {
            switch (commandName) {
                case "add" -> {
                    TaskValidator.validateAddArgs(args);
                    Task newTask = command.add(args[1]);
                    System.out.printf("Tarea agregada exitosamente (ID: %d)", newTask.getId());
                }
                case "update" -> {
                    TaskValidator.validateUpdateArgs(args);
                    long updateId = TaskValidator.parseLongId(args[1]);
                    command.update(args[2], updateId);
                    System.out.printf("Tarea actualizada exitosamente (ID: %d)", updateId);
                }
                case "mark-in-progress" -> {
                    TaskValidator.validateMarkStatusArgs(args);
                    long inProgressId = TaskValidator.parseLongId(args[1]);
                    command.modifyStatus(Status.IN_PROGRESS, inProgressId);
                    System.out.printf("Tarea marcada como en progreso exitosamente (ID: %d)", inProgressId);
                }
                case "mark-done" -> {
                    TaskValidator.validateMarkStatusArgs(args);
                    long doneId = TaskValidator.parseLongId(args[1]);
                    command.modifyStatus(Status.DONE, doneId);
                    System.out.printf("Tarea marcada como realizada exitosamente (ID: %d)", doneId);
                }
                case "delete" -> {
                    TaskValidator.validateDeleteArgs(args);
                    long deleteId = TaskValidator.parseLongId(args[1]);
                    command.delete(deleteId);
                    System.out.printf("Tarea eliminada exitosamente (ID: %d)", deleteId);
                }
                case "list" -> {
                    TaskValidator.validateListArgs(args);
                    if (args.length == 1) {
                        System.out.println(command.list());
                    } else {
                        String status = args[1];
                        Status taskStatus = TaskValidator.parseStatus(status);
                        System.out.println(command.listBy(taskStatus));
                    }
                }
                default -> throw new InvalidCommandException(
                        String.format("El comando '%s' no es válido. Por favor, proporcione un comando válido.", commandName));
            }
        } catch (TaskOperationException toe) {
            showError(toe.getMessage());
        } catch (Exception e) {
            showError("Ocurrió un error inesperado: " + e.getMessage());
        }
    }

    private static void showError(String message) {
        System.err.println(message);
    }
}

