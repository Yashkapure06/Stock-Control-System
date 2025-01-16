package utils;

import java.util.Scanner;

public class ValidationUtil {
    public static int validateIntegerInput(Scanner input, String message) {
        while (true) {
            System.out.print(message);
            if (input.hasNextInt()) {
                return input.nextInt();
            }
            System.out.println("Invalid input. Please enter a valid integer.");
            input.next();
        }
    }
}
