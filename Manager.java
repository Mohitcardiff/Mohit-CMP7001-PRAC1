package com.baymotors.users;

import com.baymotors.management.Manufacturer;
import com.baymotors.tasks.Task;

public class Manager extends User {
    public Manager(int userID, String name, String email) {
        super(userID, name, email);
    }

    public void assignTask(Task task, int mechanicID) {
        System.out.println("Task ID: " + task.getTaskID() + " assigned to Mechanic ID: " + mechanicID);
    }

    public void addManufacturer(Manufacturer manufacturer) {
        System.out.println("Manufacturer " + manufacturer.getName() + " added to the system.");
    }

    public void postOffer(String offerDetails) {
        System.out.println("Offer posted: " + offerDetails);
    }

    @Override
    public void displayRole() {
        System.out.println("Manager: " + name);
    }
}