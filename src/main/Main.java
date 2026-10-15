package main;

import java.util.*;
import model.*;
import strategy.*;
import manager.*;
import exception.*;
import util.Validations;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final ParcelManager manager = ParcelManager.getInstance();

    private static void printDivider() {
        System.out.println("-".repeat(44));
    }

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = Validations.readIntInRange(sc, "Enter choice: ", 1, 5);
            printDivider();
            System.out.println();
            switch (choice) {
                case 1: 
                    addParcel(); 
                    break;
                case 2: 
                    viewAll(); 
                    break;
                case 3: 
                    changeService(); 
                    break;
                case 4: 
                    markDelivered(); 
                    break;
                case 5:
                    System.out.println("Exiting the system. Goodbye!");
                    running = false;
                    break;
            }
        }
    }

    private static void printMenu() {
        printDivider();
        System.out.println("========== PARCEL DELIVERY SYSTEM ==========");
        printDivider();
        System.out.println("1) Add Parcel");
        System.out.println("2) View All Parcels");
        System.out.println("3) Change Delivery Service");
        System.out.println("4) Mark as Delivered");
        System.out.println("5) Exit");
        printDivider();

    }

    // ---------- Menu operations ----------

    private static void addParcel() {
        printDivider();
        System.out.println("Parcel types:\n1) Document\n2) Box\n3) Fragile");
        printDivider();
        int type = Validations.readIntInRange(sc, "Select parcel type: ", 1, 3);

        String tracking = Validations.readTrackingNumber(sc, "Tracking Number (e.g. ABC0001): ", manager::exists);

        String receiver = Validations.readNonEmpty(sc, "Receiver Name: ");
        double maxWeight = (type == 1) ? 0.5 : (type == 2) ? 50 : 30;
        double weight = Validations.readWeight(sc, "Weight in kg (max " + maxWeight + "): ", maxWeight);

        Parcel parcel = null;
        try {
            if (type == 1) {
                int pages = Validations.readIntInRange(sc, "Number of Pages (1-100): ", 1, 100);
                parcel = new Document(tracking, receiver, weight, pages);
            } else {
                int l = Validations.readPositiveInt(sc, "Length (cm): ");
                int w = Validations.readPositiveInt(sc, "Width (cm): ");
                int h = Validations.readPositiveInt(sc, "Height (cm): ");
                if (type == 2) {
                    parcel = new Box(tracking, receiver, weight, l, w, h);
                } else {
                    String note = Validations.readNonEmpty(sc, "Handling Note: ");
                    parcel = new Fragile(tracking, receiver, weight, l, w, h, note);
                }
            }
        } catch (InvalidParcelException e) {
            System.out.println(e.getMessage());
            return;
        }
        
        printDivider();
        boolean valid = false;
        while (!valid) {
            DeliveryStrategy service = selectService();
            try {
                parcel.setDeliveryService(service);
                valid = true;
            } catch (InvalidDeliveryServiceException e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            manager.addParcel(parcel);
            printDivider();
            System.out.printf("Parcel added successfully! \nDelivery fee: Php %.2f%n", parcel.getDeliveryFee());
            printDivider();
            System.out.println();
        } catch (DuplicateTrackingNumberException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewAll() {
        List<Parcel> list = manager.getAllParcels();
        if (list.isEmpty()) {
            System.out.println("No parcels in the system.");
            return;
        }
        String line = "-".repeat(94);
        System.out.println(line);
        System.out.printf("%-10s %-10s %-18s %-12s %-10s %-14s %-10s%n",
                "Tracking", "Type", "Receiver", "Chrg. Wt(kg)", "Service", "Fee (Php)", "Status");
        System.out.println(line);
        for (Parcel p : list) {
            System.out.printf("%-10s %-10s %-18s %-12.1f %-10s %-14.2f %-10s%n",
                    p.getTrackingNumber(), p.getTypeName(), truncate(p.getReceiverName(), 18),
                    p.getChargeableWeight(), p.getDeliveryService().getName(),
                    p.getDeliveryFee(), p.getStatus());
        }
        System.out.println(line);
        System.out.println();
    }

    private static void changeService() {
        printDivider();
        String tracking = Validations.readNonEmpty(sc, "Tracking Number: ").toUpperCase();
        printDivider();
        try {
            Parcel p = manager.findParcel(tracking);
            if (p.isDelivered()) {
                System.out.println("Parcel already delivered!");
                return;
            }
            DeliveryStrategy service = selectService();
            manager.changeService(tracking, service);
            System.out.printf("Delivery service changed! \nNew fee: Php %.2f%n", p.getDeliveryFee());
        } catch (ParcelException e) {
            System.out.println(e.getMessage());
        }
        printDivider();
        System.out.println();
    }

    private static void markDelivered() {
        printDivider();
        String tracking = Validations.readNonEmpty(sc, "Tracking Number: ").toUpperCase();
        printDivider();
        try {
            manager.markDelivered(tracking);
            System.out.println("Parcel delivered successfully!");
        } catch (ParcelException e) {
            System.out.println(e.getMessage());
        }
        printDivider();
        System.out.println();
    }

    // ---------- Input helpers (validation) ----------

    private static DeliveryStrategy selectService() {
        System.out.println("Delivery services:\n1) Standard\n2) Express\n3) Same Day");
        printDivider();
        int c = Validations.readIntInRange(sc, "Select delivery service: ", 1, 3);
        if (c == 1) return new StandardDelivery();
        if (c == 2) return new ExpressDelivery();
        return new SameDayDelivery();

    }


    private static String truncate(String s, int len) {
        return s.length() <= len ? s : s.substring(0, len - 1) + "~";
    }
}