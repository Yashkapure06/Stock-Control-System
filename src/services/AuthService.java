package services;

import models.User;
import repositories.UserRepository;

import java.util.Optional;
import java.util.Scanner;

public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository; 
    }

    public User register(Scanner input) {
        System.out.print("Enter email: ");
        String email = input.nextLine();
        System.out.print("Enter username: ");
        String username = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();

        // **CHANGE**: Check if email is already registered
        if (userRepository.findUserByEmail(email).isPresent()) {
            System.out.println("Email already registered! Please use a different email."); 
            return null; 
        }

        if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            System.out.println("All fields are required for registration."); 
            return null;
        }

        User newUser = new User(email, username, password); 
        userRepository.saveUser(newUser);
        System.out.println("Registration successful! You can now log in.");
        return newUser; 
    }

    public User login(Scanner input) {
        System.out.print("Enter email: ");
        String email = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();

        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            System.out.println("Login successful!"); 
            return user.get(); 
        }

        System.out.println("Invalid email or password. Please try again.");
        return null; 
    }
}
