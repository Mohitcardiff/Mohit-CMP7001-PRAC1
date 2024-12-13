package com.baymotors.management;

import java.util.ArrayList;
import java.util.List;

public class Manufacturer {
    private int manufacturerID;
    private String name;
    private String location;
    private List<PartSupplier> suppliers;

    public Manufacturer(int manufacturerID, String name, String location) {
        this.manufacturerID = manufacturerID;
        this.name = name;
        this.location = location;
        this.suppliers = new ArrayList<>();
    }

    public int getManufacturerID() {
        return manufacturerID;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public List<PartSupplier> getSuppliers() {
        return suppliers;
    }

    public void addPartSupplier(PartSupplier supplier) {
        suppliers.add(supplier);
        System.out.println("Part supplier " + supplier.getName() + " added to Manufacturer " + name);
    }
}