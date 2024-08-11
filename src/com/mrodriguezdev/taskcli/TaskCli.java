package com.mrodriguezdev.taskcli;

import com.mrodriguezdev.taskcli.command.Command;
import com.mrodriguezdev.taskcli.command.CommandImpl;
import com.mrodriguezdev.taskcli.exception.InvalidCommandException;
import com.mrodriguezdev.taskcli.exception.InvalidStatusException;
import com.mrodriguezdev.taskcli.model.Status;
import com.mrodriguezdev.taskcli.model.Task;

public class TaskCli {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("Please provide arguments or parameters to execute an action.");
            System.exit(1);
        }

        String commandName = args[0];

        Command command = new CommandImpl();
        switch (commandName) {
            case "add" -> {
                if (args.length < 2) {
                    System.err.println("Please provide a task description to add.");
                    System.exit(1);
                }
                Task newTask = command.add(args[1]);
                System.out.printf("Task added successfully (ID: %d)%n", newTask.getId());
            }
            case "list" -> {
                if (args.length < 2) System.out.println(command.list());
                else {
                    String status = args[1];
                    if (Status.TODO.get().equals(status)) System.out.println(command.listBy(Status.TODO));
                    else if (Status.IN_PROGRESS.get().equals(status)) System.out.println(command.listBy(Status.IN_PROGRESS));
                    else if (Status.DONE.get().equals(status)) System.out.println(command.listBy(Status.DONE));
                    else throw new InvalidStatusException(
                            String.format("The status '%s' is invalid. Please provide a valid status.", status));
                }
            }
            default -> throw new InvalidCommandException(
                    String.format("The command '%s' is invalid. Please provide a valid command.", commandName));
        }
    }
}
