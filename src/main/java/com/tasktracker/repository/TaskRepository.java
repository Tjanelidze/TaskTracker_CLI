package com.tasktracker.repository;

import com.tasktracker.model.Task;
import com.tasktracker.model.TaskStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TaskRepository {
    private final Path filePath = Path.of("tasks.json");

    public List<Task> getAll() {
        try {
            if (!Files.exists(filePath) || Files.size(filePath) == 0) {
                return Collections.emptyList();
            }
            String tasks = Files.readString(filePath);
            String omitOpeningBracket = tasks.replace("[", "");
            String payload = omitOpeningBracket.replace("]", "").trim();

            if (payload.isEmpty()) {
                return Collections.emptyList();
            }

            String[] sterilizedTasks = payload.split("},\\s*");
            List<Task> result = new ArrayList<>();

            for (String rawJson : sterilizedTasks) {
                String idStr = extractValue(rawJson, "\"id\": \"");
                String descStr = extractValue(rawJson, "\"description\": \"");
                String statusStr = extractValue(rawJson, "\"status\": \"");
                String createdStr = extractValue(rawJson, "\"createdAt\": \"");
                String updatedStr = extractValue(rawJson, "\"updatedAt\": \"");

                LocalDateTime createdAt = LocalDateTime.parse(createdStr);
                LocalDateTime updatedAt = LocalDateTime.parse(updatedStr);
                UUID id = UUID.fromString(idStr);
                TaskStatus status = TaskStatus.valueOf(statusStr);

                Task task = new Task(id, descStr, status, createdAt, updatedAt);
                result.add(task);
            }

            return new ArrayList<>(result);
        } catch (IOException | DateTimeParseException | IllegalArgumentException e) {
            throw new RuntimeException("Failed to read tasks from file", e);
        }
    }

    private String extractValue(String rawJson, String key) {
        int keyIndex = rawJson.indexOf(key);
        if (keyIndex == -1) return "";

        int valueStart = keyIndex + key.length();
        int valueEnd = rawJson.indexOf("\"", valueStart);

        return rawJson.substring(valueStart, valueEnd);
    }


    public void saveAll(List<Task> tasks) {
        StringBuilder sb = new StringBuilder("[");
        int size = tasks.size();

        for (int i = 0; i < size; i++) {
            sb.append(formatToJson(tasks.get(i)));

            if (i < size - 1) {
                sb.append(",");
            }
        }
        sb.append("\n]");

        try {
            Files.writeString(filePath, sb.toString());
        } catch (IOException e) {
            throw new RuntimeException("Could not save tasks to: " + filePath, e);
        }
    }

    private String formatToJson(Task task) {
        return "  \n  {\n" +
                "    \"id\": \"" + task.getId() + "\",\n" +
                "    \"description\": \"" + escape(task.getDescription()) + "\",\n" +
                "    \"status\": \"" + task.getStatus() + "\",\n" +
                "    \"createdAt\": \"" + task.getCreatedAt() + "\",\n" +
                "    \"updatedAt\": \"" + task.getUpdatedAt() + "\"\n" +
                "  }";
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");
    }
}
