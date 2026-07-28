package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MaintenanceJob {

    private String description;

    @JsonProperty("due_date")
    private String dueDate;

    private String priority;

    public MaintenanceJob() {
        // Empty constructor for serialization
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}