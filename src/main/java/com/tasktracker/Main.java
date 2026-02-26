package com.tasktracker;

import com.tasktracker.service.TaskService;

public class Main {
    /**
     * Entry point for the CLI; parses command-line arguments and executes a task command.
     *
     * Expects at least two arguments: a command and a task description. If fewer than two
     * arguments are provided, prints a usage message and exits. Supports the "add" command,
     * which creates a TaskService and adds a task with the given description; any other
     * command prints an "Unknown command" message.
     *
     * @param args the command-line arguments: args[0] is the command (e.g., "add"), args[1] is the task description
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: task-cli <command> <description>");
            return;
        }

        String command = args[0];
        String description = args[1];
        TaskService task = new TaskService();


        if (command.equals("add")) {
            task.add(description);
        } else {
            System.out.println("Unknown command: " + command);
        }
    }
}