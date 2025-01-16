package testcases.random.services;

import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.UserRepository;
import services.AuthService;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceRandomTest {

    private UserRepository userRepository;
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepository(); // Use the actual implementation
        authService = new AuthService(userRepository);
    }

    @Test
    public void testRegister_SuccessfulRegistration() {
        // Simulate user input for email, username, and password
        String input = "test@example.com\ntestuser\npassword123\n";
        Scanner scanner = new Scanner(input);

        // Ensure the repository is empty before the test
        assertFalse(userRepository.findUserByEmail("test@example.com").isPresent(), 
            "Repository should not contain the user before registration");

        // Call the register method
        User user = authService.register(scanner);

        // Assert that a user is successfully registered
        assertNotNull(user, "User should not be null after successful registration");
        assertEquals("test@example.com", user.getEmail(), "Email should match the input");
        assertEquals("testuser", user.getUsername(), "Username should match the input");
        assertEquals("password123", user.getPassword(), "Password should match the input");

        // Verify the user is saved in the repository
        Optional<User> savedUser = userRepository.findUserByEmail("test@example.com");
        assertTrue(savedUser.isPresent(), "User should be saved in the repository");
        assertEquals("test@example.com", savedUser.get().getEmail(), "Saved user's email should match");
        assertEquals("testuser", savedUser.get().getUsername(), "Saved user's username should match");
        assertEquals("password123", savedUser.get().getPassword(), "Saved user's password should match");
    }


    @Test
    public void testRegister_EmailAlreadyRegistered() {
        String input = "test@example.com\ntestuser\npassword123\n";
        Scanner scanner = new Scanner(input);

        // Add an existing user to the repository
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
    public void testRegister_InvalidPassword() {
        String input = "test@example.com\ntestuser\n\n"; // Empty password
        Scanner scanner = new Scanner(input);

        User user = authService.register(scanner);

        assertNull(user, "User should not be registered with an empty password");
    }


    @Test
    public void testLogin_SuccessfulLogin() {
        String input = "test@example.com\npassword123\n";
        Scanner scanner = new Scanner(input);

        // Add a user to the repository for login
        User mockUser = new User("test@example.com", "testuser", "password123");
        userRepository.saveUser(mockUser);

        User user = authService.login(scanner);

        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
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

        // Add a user to the repository for login
        User mockUser = new User("test@example.com", "testuser", "password123");
        userRepository.saveUser(mockUser);

        User user = authService.login(scanner);

        assertNull(user);
    }

    @Test
    public void testLogin_EmptyFields() {
        String input = "\n\n"; // Both email and password are empty
        Scanner scanner = new Scanner(input);

        User user = authService.login(scanner);

        assertNull(user);
    }

    @Test
    public void testLogin_WhitespaceFields() {
        String input = "   \n   \n"; // Email and password are whitespace
        Scanner scanner = new Scanner(input);

        User user = authService.login(scanner);

        assertNull(user);
    }



    @Test
    public void testRegister_LongInputs() {
        String email = "longemailaddress@example.com";
        String username = "verylongusernameinputtest";
        String password = "verysecurelongpassword";
        String input = email + "\n" + username + "\n" + password + "\n";

        Scanner scanner = new Scanner(input);

        User user = authService.register(scanner);

        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertTrue(userRepository.findUserByEmail(email).isPresent());
    }
}
