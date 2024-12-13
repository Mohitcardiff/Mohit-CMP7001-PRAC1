package com.baymotors.tasks;

public class Task {
    private int taskID;
    private String description;
    private String priority;
    private String status;

    // Constructor with three parameters
    public Task(int taskID, String description, String priority) {
        this.taskID = taskID;
        this.description = description;
        this.priority = priority;
        this.status = "Pending";
    }

    // Constructor with four parameters
    public Task(int taskID, String description, String priority, String status) {
        this.taskID = taskID;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    // Getters
    public int getTaskID() {
        return taskID;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public void assignToMechanic(int mechanicID) {
        System.out.println("Task ID: " + taskID + " assigned to Mechanic ID: " + mechanicID);
    }

    public void completeTask() {
        this.status = "Completed";
        System.out.println("Task ID: " + taskID + " is now completed.");
    }
}