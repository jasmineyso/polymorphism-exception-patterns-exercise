package util;

import java.util.Scanner;
import java.util.function.Predicate;

public class Validations {

    private Validations() { }

    public static String readNonEmpty(Scanner sc, String prompt) {
        String input = "";
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty.");
            } else {
                valid = true;
            }
        }
        return input;
    }

    public static String readTrackingNumber(Scanner sc, String prompt, Predicate<String> alreadyExists) {
        String input = "";
        boolean valid = false;
        while (!valid) {
            input = readNonEmpty(sc, prompt).toUpperCase();
            if (!input.matches("ABC\\d{4}")) {
                System.out.println("Invalid format! Must be 'ABC' followed by 4 digits.");
            } else if (alreadyExists.test(input)) {
                System.out.println("Tracking number already exists!");
            } else {
                valid = true;
            }
        }
        return input;
    }

    public static int readIntInRange(Scanner sc, String prompt, int min, int max) {
        int value = min;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (!input.matches("0|[1-9]\\d*")) {
                System.out.println("Invalid input! Please enter a whole number.");
            } else {
                try {
                    value = Integer.parseInt(input);
                    if (value >= min && value <= max) {
                        valid = true;
                    } else {
                        System.out.println("Please enter a number from " + min + " to " + max + ".");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Number is too large.");
                }
            }
        }
        return value;
    }

    public static int readPositiveInt(Scanner sc, String prompt) {
        int value = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (!input.matches("0|[1-9]\\d*")) {
                System.out.println("Invalid input! Please enter a whole number.");
            } else {
                try {
                    value = Integer.parseInt(input);
                    if (value > 0) {
                        valid = true;
                    } else {
                        System.out.println("Value must be greater than 0.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Number is too large.");
                }
            }
        }
        return value;
    }

    public static double readWeight(Scanner sc, String prompt, double max) {
        double value = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (!input.matches("(0|[1-9]\\d*)(\\.\\d+)?")) {
                System.out.println("Invalid input! Use digits only, e.g. 1.5 or 20.");
            } else {
                value = Double.parseDouble(input);
                if (!(value > 0)) {
                    System.out.println("Weight must be greater than 0.");
                } else if (value > max) {
                    System.out.println("Weight cannot exceed " + max + " kg for this parcel type.");
                } else {
                    valid = true;
                }
            }
        }
        return value;
    }
}