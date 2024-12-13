package com.baymotors.management;

public class NotificationService {
    public void sendNotification(int customerID, String message) {
        System.out.println("Notification sent to Customer ID: " + customerID + " - Message: " + message);
    }
}