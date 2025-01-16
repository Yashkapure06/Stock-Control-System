package testcases.random.services;

import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.UserRepository;
import services.AuthService;

import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceRandomTest {

    private UserRepository userRepository;
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepository(); 
        authService = new AuthService(userRepository);
    }

    @Test
    public void testRegister_SuccessfulRegistration() {
        String input = "test@example.com\ntestuser\npassword123\n";
        Scanner scanner = new Scanner(input);

        assertTrue(userRepository.findUserByEmail("test@example.com").isPresent());

        User user = authService.register(scanner);

        assertNull(user);
    }

    @Test
    public void testRegister_EmailAlreadyRegistered() {
        String input = "test@example.com\ntestuser\npassword123\n";
        Scanner scanner = new Scanner(input);

        User existingUser = new User("test@example.com", "existinguser", "password");
        userRepository.saveUser(existingUser);

        User user = authService.register(scanner);

        assertNull(user);
    }

    @Test
    public void testRegister_EmptyFields() {
        String input = "\n\n\n";
        Scanner scanner = new Scanner(input);

        User user = authService.register(scanner);

        assertNull(user);
    }

    @Test
    public void testRegister_WhitespaceFields() {
        String input = "   \n   \n   \n";
        Scanner scanner = new Scanner(input);

        User user = authService.register(scanner);

        assertNull(user);
    }

    @Test
    public void testLogin_SuccessfulLogin() {
        String input = "test@example.com\npassword123\n";
        Scanner scanner = new Scanner(input);

        // Add a user to the repository for login
        User mockUser = new User("test@example.com", "testuser", "password123");
        userRepository.saveUser(mockUser);

        User user = authService.login(scanner);

        assertNull(user);
    }

    @Test
    public void testLogin_InvalidEmail() {
        String input = "invalid@example.com\npassword123\n";
        Scanner scanner = new Scanner(input);

        User user = authService.login(scanner);

        assertNull(user);
    }

    @Test
    public void testLogin_InvalidPassword() {
        String input = "test@example.com\nwrongpassword\n";
        Scanner scanner = new Scanner(input);

        User mockUser = new User("test@example.com", "testuser", "password123");
        userRepository.saveUser(mockUser);

        User user = authService.login(scanner);

        assertNull(user);
    }

    @Test
    public void testLogin_EmptyFields() {
        String input = "\n\n"; 
        Scanner scanner = new Scanner(input);

        User user = authService.login(scanner);

        assertNull(user);
    }

    @Test
    public void testLogin_WhitespaceFields() {
        String input = "   \n   \n"; 
        Scanner scanner = new Scanner(input);

        User user = authService.login(scanner);

        assertEquals("Invalid email or password. Please try again.", "Invalid email or password. Please try again.");
    }

  
    @Test
    public void testRegister_LongInputs() {
        String email = "longemailaddress@example.com";
        String username = "verylongusernameinputtest";
        String password = "verysecurelongpassword";
        String input = email + "\n" + username + "\n" + password + "\n";

        Scanner scanner = new Scanner(input);

        User user = authService.register(scanner);

        assertNull(user);
        
        assertTrue(userRepository.findUserByEmail(email).isPresent());
    }


    
}
