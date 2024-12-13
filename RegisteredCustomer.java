package com.baymotors.users;

public class RegisteredCustomer extends User {
    private String membershipStatus;

    public RegisteredCustomer(int userID, String name, String email, String membershipStatus) {
        super(userID, name, email);
        this.membershipStatus = membershipStatus;
    }

    public String getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(String membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    public void getBenefits() {
        System.out.println("Accessing benefits for " + membershipStatus + " membership.");
    }

    @Override
    public void displayRole() {
        System.out.println("Registered Customer: " + getName());
    }

    public int getCustomerID() {
        return getUserID();
    }
}