package com.mrodriguezdev.taskcli;

import com.mrodriguezdev.taskcli.command.Command;
import com.mrodriguezdev.taskcli.command.CommandImpl;
import com.mrodriguezdev.taskcli.exception.InvalidCommandException;
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
            default -> throw new InvalidCommandException(
                    String.format("The command '%s' is invalid. Please provide a valid command.", commandName));
        }
    }
}
