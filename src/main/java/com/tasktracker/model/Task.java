package com.tasktracker.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Task {
    private UUID id;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task(UUID id, String description, TaskStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        setDescription(description);
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Task(UUID id, String description, TaskStatus status) {
        this.id = id;
        setDescription(description);
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) throw new IllegalArgumentException("Description cannot be null!");

        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "  \n  {\n" +
                "    \"id\": \"" + id + "\",\n" +
                "    \"description\": \"" + description + "\",\n" +
                "    \"status\": \"" + status + "\",\n" +
                "    \"createdAt\": \"" + createdAt + "\",\n" +
                "    \"updatedAt\": \"" + updatedAt + "\"\n" +
                "  }\n";
    }
}
