package com.baymotors.users;

public class Mechanic extends User {
    public Mechanic(int userID, String name, String email) {
        super(userID, name, email);
    }

    public void viewTasks() {
        System.out.println(name + " is viewing assigned tasks.");
    }

    public void updateTaskStatus(int taskID, String status) {
        System.out.println("Task ID: " + taskID + " has been updated to: " + status);
    }

    @Override
    public void displayRole() {
        System.out.println("Mechanic: " + name);
    }
}
