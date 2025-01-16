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
        userRepository = new UserRepository(); // Use the actual implementation
        authService = new AuthService(userRepository);
    }

    @Test
    public void testRegister_SuccessfulRegistration() {
        String input = "test@example.com\ntestuser\npassword123\n";
        Scanner scanner = new Scanner(input);

        // Ensure the repository is empty for this test
        assertFalse(userRepository.findUserByEmail("test@example.com").isPresent());

        User user = authService.register(scanner);

        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertTrue(userRepository.findUserByEmail("test@example.com").isPresent());
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
    public void testRegister_BoundaryEmail() {
        String input = "a@b.c\ntestuser\npassword123\n";
        Scanner scanner = new Scanner(input);

        User user = authService.register(scanner);

        assertNotNull(user);
        assertEquals("a@b.c", user.getEmail());
        assertTrue(userRepository.findUserByEmail("a@b.c").isPresent());
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

    @Test
    public void testRegister_ExcessivelyLongInputs() {
        String email = "a".repeat(500) + "@example.com";
        String username = "b".repeat(500);
        String password = "c".repeat(500);
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
