package com.baymotors.management;

public class PartSupplier {
    private int supplierID;
    private String name;
    private String contactDetails;

    public PartSupplier(int supplierID, String name, String contactDetails) {
        this.supplierID = supplierID;
        this.name = name;
        this.contactDetails = contactDetails;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public String getName() {
        return name;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void supplyParts() {
        System.out.println("Part supplier " + name + " is supplying parts.");
    }
}