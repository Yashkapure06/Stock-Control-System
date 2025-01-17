package utils;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ValidationUtil {
	public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return Pattern.matches(passwordRegex, password);
    }

    public static boolean isValidUsername(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }
        String usernameRegex = "^[A-Za-z0-9_]{3,15}$";
        return Pattern.matches(usernameRegex, username);
    }

    public static int validateIntegerInput(java.util.Scanner input, String message) {
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