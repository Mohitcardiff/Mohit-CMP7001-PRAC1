package com.baymotors.vehicles;

public class Vehicle {
    private int vehicleID;
    private String make;
    private String model;

    public Vehicle(int vehicleID, String make, String model) {
        this.vehicleID = vehicleID;
        this.make = make;
        this.model = model;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public void registerVehicle() {
        System.out.println("Vehicle Registered: " + make + " " + model);
    }
}