package com.tasktracker.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Task {
    private UUID id;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Create a Task with the given id, description, and status and initialize creation and update timestamps.
     *
     * @param id          the unique identifier for the task
     * @param description the task description; must not be null
     * @param status      the initial status of the task
     * @throws IllegalArgumentException if {@code description} is null
     */
    public Task(UUID id, String description, TaskStatus status) {
        this.id = id;
        setDescription(description);
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    /**
     * Gets the task's unique identifier.
     *
     * @return the task's UUID
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the task's unique identifier.
     *
     * @param id the UUID to assign as this task's identifier
     */
    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Sets the task's description.
     *
     * @param description the new description text; must not be null
     * @throws IllegalArgumentException if {@code description} is null
     */
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

    /**
     * Set the timestamp when the task was last updated.
     *
     * @param updatedAt the timestamp to record as the task's last-updated time
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * String representation of the task including id, description, status, createdAt, and updatedAt.
     *
     * @return a string containing the task's id, description, status, createdAt, and updatedAt
     */
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
