package com.tasktracker.service;

import com.tasktracker.model.Task;
import com.tasktracker.model.TaskStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class TaskService {


    /**
     * Adds a new task with the given description to the tasks.json file-backed store.
     *
     * The method ensures the tasks.json file exists (initializing it as an empty JSON array if necessary),
     * appends a newly created Task (with a generated UUID and TODO status) to the array, and writes the updated
     * content back to the file. On success it prints the new task's ID; I/O errors are caught and reported.
     *
     * @param description the text description for the new task
     */
    public void add(String description) {
        Path filePath = Path.of("tasks.json");

        try {
            if (!Files.exists(filePath) || Files.size(filePath) == 0) {
                Files.writeString(filePath, "[]");
            }

            Task newTask = new Task(UUID.randomUUID(), description, TaskStatus.TODO);
            String content = Files.readString(filePath);
            String updatedContent = getString(content, newTask);

            Files.writeString(filePath, updatedContent);

            System.out.println("Task added successfully (ID: " + newTask.getId() + ")");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Appends the given task as a JSON object to the JSON array contained in `content`.
     *
     * @param content the textual JSON array to which the task will be appended (must contain a closing ']')
     * @param newTask the task to serialize and append
     * @return the updated JSON array string with `newTask` added (properly inserts a comma if the array was not empty)
     */
    private static String getString(String content, Task newTask) {
        int lastBracketIndex = content.lastIndexOf(']');
        String beginning = content.substring(0, lastBracketIndex);

        String comma = beginning.trim().endsWith("[") ? "" : ",";

        String newTaskJson = String.format(
                "{\"id\":\"%s\",\"description\":\"%s\",\"status\":\"%s\",\"createdAt\":\"%s\",\"updatedAt\":\"%s\"}",
                newTask.getId().toString(),
                escapeJson(newTask.getDescription()),
                newTask.getStatus(),
                newTask.getCreatedAt(),
                newTask.getUpdatedAt()
        );

        return beginning + comma + newTaskJson + "]";
    }


    /**
     * Escape characters in a string so it can be safely embedded in JSON.
     *
     * @param value the input string to escape; if {@code null}, treated as an empty string
     * @return the input with backslashes, double quotes, newlines, carriage returns, and tabs
     *         replaced by their JSON escape sequences (e.g. `\n`, `\r`, `\t`, `\"`, `\\`)
     */
    private static String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
