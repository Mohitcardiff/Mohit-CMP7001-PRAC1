package com.baymotors.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.baymotors.exceptions.InvalidCustomerException;
import com.baymotors.exceptions.ManufacturerNotFoundException;
import com.baymotors.exceptions.MechanicNotFoundException;
import com.baymotors.exceptions.VehicleNotFoundException;
import com.baymotors.management.Manufacturer;
import com.baymotors.management.NotificationService;
import com.baymotors.management.PartSupplier;
import com.baymotors.tasks.Task;
import com.baymotors.users.Manager;
import com.baymotors.users.Mechanic;
import com.baymotors.users.RegisteredCustomer;
import com.baymotors.vehicles.Vehicle;

public class BayMotorsApp {
    private static Map<Integer, RegisteredCustomer> customers = new HashMap<>();
    private static Map<Integer, Vehicle> vehicles = new HashMap<>();
    private static Map<Integer, Mechanic> mechanics = new HashMap<>();
    private static Map<Integer, Task> tasks = new HashMap<>();
    private static Map<Integer, Manufacturer> manufacturers = new HashMap<>();
    private static Manager manager;

    public static void main(String[] args) {
        manager = new Manager(1, "John Doe", "manager@baymotors.com");
        mechanics.put(1, new Mechanic(1, "Jane Smith", "mechanic@baymotors.com"));
        Scanner scanner = new Scanner(System.in);

        try {
            // Load data from CSV files at the start
            loadCustomersFromCSV();
            loadVehiclesFromCSV();
            loadTasksFromCSV();

            while (true) {
                System.out.println("\nWelcome to Bay Motors System!");
                System.out.println("1. Register Customer");
                System.out.println("2. Register Vehicle");
                System.out.println("3. Assign Task");
                System.out.println("4. Complete Task");
                System.out.println("5. Send Notification");
                System.out.println("6. Add Manufacturer");
                System.out.println("7. Add Part Supplier");
                System.out.println("8. View All Customers");
                System.out.println("9. View All Vehicles");
                System.out.println("10. View All Tasks");
                System.out.println("11. Save Data to CSV Files");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");

                int option = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                try {
                    switch (option) {
                        case 1:
                            registerCustomer(scanner);
                            break;
                        case 2:
                            registerVehicle(scanner);
                            break;
                        case 3:
                            assignTask(scanner);
                            break;
                        case 4:
                            completeTask(scanner);
                            break;
                        case 5:
                            sendNotification(scanner);
                            break;
                        case 6:
                            addManufacturer(scanner);
                            break;
                        case 7:
                            addPartSupplier(scanner);
                            break;
                        case 8:
                            viewAllCustomers();
                            break;
                        case 9:
                            viewAllVehicles();
                            break;
                        case 10:
                            viewAllTasks();
                            break;
                        case 11:
                            saveDataToCSV();
                            break;
                        case 0:
                            System.out.println("Exiting system...");
                            return;
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } finally {
            scanner.close();
        }
    }

    private static void registerCustomer(Scanner scanner) throws InvalidCustomerException {
        System.out.print("Enter Customer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (customers.containsKey(id)) {
            throw new InvalidCustomerException("Customer ID " + id + " already exists.");
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine().trim();

        if (name.isEmpty() || email.isEmpty()) {
            throw new InvalidCustomerException("Customer name or email cannot be empty.");
        }

        customers.put(id, new RegisteredCustomer(id, name, email, "Walk-in"));
        System.out.println("Customer registered successfully!");
    }

    private static void registerVehicle(Scanner scanner) throws VehicleNotFoundException {
        System.out.print("Enter Vehicle ID: ");
        int vehicleID = scanner.nextInt();
        scanner.nextLine();

        if (vehicles.containsKey(vehicleID)) {
            throw new VehicleNotFoundException("Vehicle ID " + vehicleID + " already exists.");
        }

        System.out.print("Enter Make: ");
        String make = scanner.nextLine().trim();
        System.out.print("Enter Model: ");
        String model = scanner.nextLine().trim();

        System.out.print("Enter Customer ID: ");
        int customerID = scanner.nextInt();

        RegisteredCustomer customer = customers.get(customerID);
        if (customer == null) {
            throw new VehicleNotFoundException("Customer ID " + customerID + " not found.");
        }

        Vehicle vehicle = new Vehicle(vehicleID, make, model);
        vehicles.put(vehicleID, vehicle);
        System.out.println("Vehicle registered successfully!");
    }

    private static void assignTask(Scanner scanner) throws MechanicNotFoundException {
        System.out.print("Enter Task ID: ");
        int taskID = scanner.nextInt();
        scanner.nextLine();

        if (tasks.containsKey(taskID)) {
            throw new MechanicNotFoundException("Task ID " + taskID + " already exists.");
        }

        System.out.print("Enter Description: ");
        String description = scanner.nextLine().trim();
        System.out.print("Enter Priority (High/Medium/Low): ");
        String priority = scanner.nextLine().trim();

        System.out.print("Enter Mechanic ID: ");
        int mechanicID = scanner.nextInt();

        Mechanic mechanic = mechanics.get(mechanicID);
        if (mechanic == null) {
            throw new MechanicNotFoundException("Mechanic ID " + mechanicID + " not found.");
        }

        Task task = new Task(taskID, description, priority, "Pending");
        tasks.put(taskID, task);
        manager.assignTask(task, mechanicID);
        System.out.println("Task assigned successfully!");
    }

    private static void completeTask(Scanner scanner) throws VehicleNotFoundException {
        System.out.print("Enter Task ID to complete: ");
        int taskID = scanner.nextInt();

        Task task = tasks.get(taskID);
        if (task == null) {
            throw new VehicleNotFoundException("Task ID " + taskID + " not found.");
        }

        task.completeTask();
        System.out.println("Task completed successfully!");
    }

    private static void sendNotification(Scanner scanner) throws InvalidCustomerException {
        System.out.print("Enter Customer ID: ");
        int customerID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Notification Message: ");
        String message = scanner.nextLine().trim();

        RegisteredCustomer customer = customers.get(customerID);
        if (customer == null) {
            throw new InvalidCustomerException("Customer ID " + customerID + " not found.");
        }

        NotificationService notificationService = new NotificationService();
        notificationService.sendNotification(customerID, message);
        System.out.println("Notification sent successfully!");
    }

    private static void addManufacturer(Scanner scanner) {
        System.out.print("Enter Manufacturer ID: ");
        int manufacturerID = scanner.nextInt();
        scanner.nextLine();

        if (manufacturers.containsKey(manufacturerID)) {
            System.out.println("Manufacturer ID " + manufacturerID + " already exists.");
            return;
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Location: ");
        String location = scanner.nextLine().trim();

        Manufacturer manufacturer = new Manufacturer(manufacturerID, name, location);
        manufacturers.put(manufacturerID, manufacturer);
        System.out.println("Manufacturer added successfully!");
    }

    private static void addPartSupplier(Scanner scanner) throws ManufacturerNotFoundException {
        System.out.print("Enter Manufacturer ID to add the supplier: ");
        int manufacturerID = scanner.nextInt();
        scanner.nextLine();

        Manufacturer manufacturer = manufacturers.get(manufacturerID);
        if (manufacturer == null) {
            throw new ManufacturerNotFoundException("Manufacturer ID " + manufacturerID + " not found.");
        }

        System.out.print("Enter Part Supplier ID: ");
        int supplierID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Contact Details: ");
        String contact = scanner.nextLine().trim();

        PartSupplier supplier = new PartSupplier(supplierID, name, contact);
        manufacturer.addPartSupplier(supplier);
        System.out.println("Part supplier added successfully to Manufacturer ID: " + manufacturerID);
    }

    private static void saveDataToCSV() {
        saveCustomersToCSV();
        saveVehiclesToCSV();
        saveTasksToCSV();
        System.out.println("Data saved to CSV files successfully.");
    }

    private static void saveCustomersToCSV() {
        try (FileWriter writer = new FileWriter("customers.csv")) {
            writer.write("CustomerID,Name,Email,MembershipStatus\n");
            for (RegisteredCustomer customer : customers.values()) {
                writer.write(customer.getCustomerID() + "," +
                        customer.getName() + "," +
                        customer.getEmail() + "," +
                        customer.getMembershipStatus() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving customers to CSV: " + e.getMessage());
        }
    }

    private static void saveVehiclesToCSV() {
        try (FileWriter writer = new FileWriter("vehicles.csv")) {
            writer.write("VehicleID,Make,Model\n");
            for (Vehicle vehicle : vehicles.values()) {
                writer.write(vehicle.getVehicleID() + "," +
                        vehicle.getMake() + "," +
                        vehicle.getModel() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving vehicles to CSV: " + e.getMessage());
        }
    }

    private static void saveTasksToCSV() {
        try (FileWriter writer = new FileWriter("tasks.csv")) {
            writer.write("TaskID,Description,Priority,Status\n");
            for (Task task : tasks.values()) {
                writer.write(task.getTaskID() + "," +
                        task.getDescription() + "," +
                        task.getPriority() + "," +
                        task.getStatus() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks to CSV: " + e.getMessage());
        }
    }

    private static void loadCustomersFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader("customers.csv"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String email = parts[2];
                String membership = parts[3];
                customers.put(id, new RegisteredCustomer(id, name, email, membership));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Customers file not found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading customers from CSV: " + e.getMessage());
        }
    }

    private static void loadVehiclesFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader("vehicles.csv"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String make = parts[1];
                String model = parts[2];
                vehicles.put(id, new Vehicle(id, make, model));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Vehicles file not found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading vehicles from CSV: " + e.getMessage());
        }
    }

    private static void loadTasksFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.csv"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String description = parts[1];
                String priority = parts[2];
                String status = parts[3];
                tasks.put(id, new Task(id, description, priority, status));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Tasks file not found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading tasks from CSV: " + e.getMessage());
        }
    }

    private static void viewAllCustomers() {
        System.out.println("Registered Customers:");
        customers.values().forEach(System.out::println);
    }

    private static void viewAllVehicles() {
        System.out.println("Registered Vehicles:");
        vehicles.values().forEach(System.out::println);
    }

    private static void viewAllTasks() {
        System.out.println("Assigned Tasks:");
        tasks.values().forEach(System.out::println);
    }
}
