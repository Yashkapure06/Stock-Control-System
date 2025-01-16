package testcases.random.repositories;

import repositories.UserRepository;
import models.User;
import services.AuthService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.ByteArrayInputStream;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;


class UserRepositoryRandomTest {
    private UserRepository userRepository;
    private AuthService authService;
    private Random random;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        authService = new AuthService(userRepository); // Use your unchanged AuthService
        random = new Random();
    }

    @AfterEach
    void tearDown() {
        File file = new File("E:\\Eclipse\\Stock-Control-System\\src\\resources\\users.json");
        if (file.exists()) {
            file.delete(); // Cleanup after each test
        }
    }

    private String generateRandomEmail() {
        return "user" + UUID.randomUUID() + "@example.com"; // Guarantees unique emails
    }

    private String generateRandomUsername() {
        int length = random.nextInt(10) + 5; // Random username length between 5 and 15
        return random.ints('a', 'z' + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private String generateRandomPassword() {
        int length = random.nextInt(10) + 8; // Random password length between 8 and 18
        return random.ints('a', 'z' + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private ByteArrayInputStream createInputStream(String... inputs) {
        String input = String.join("\n", inputs); // Combine inputs with newline as Scanner expects
        return new ByteArrayInputStream(input.getBytes());
    }

    @Test
    void testRegisterAndRetrieveRandomUser() {
        // Arrange
        String email = generateRandomEmail();
        String username = generateRandomUsername();
        String password = generateRandomPassword();
        System.setIn(createInputStream(email, username, password));

        // Act
        User registeredUser = authService.register(new java.util.Scanner(System.in));
        Optional<User> fetchedUser = userRepository.findUserByEmail(email);

        // Assert
        assertNotNull(registeredUser, "User should be successfully registered.");
        assertTrue(fetchedUser.isPresent(), "User should be retrievable by email.");
        assertEquals(username, fetchedUser.get().getUsername(), "Username should match.");
        assertEquals(password, fetchedUser.get().getPassword(), "Password should match.");
    }

    @Test
    void testRegisterDuplicateEmail() {
        // Arrange
        String email = generateRandomEmail();
        String username1 = generateRandomUsername();
        String password1 = generateRandomPassword();
        String username2 = generateRandomUsername();
        String password2 = generateRandomPassword();

        System.setIn(createInputStream(email, username1, password1));
        User firstUser = authService.register(new java.util.Scanner(System.in));

        System.setIn(createInputStream(email, username2, password2));
        User duplicateUser = authService.register(new java.util.Scanner(System.in));

        // Assert
        assertNotNull(firstUser, "First user should be registered successfully.");
        assertNull(duplicateUser, "Duplicate user registration should fail.");
    }

    @Test
    void testLoginValidUser() {
        // Arrange
        String email = generateRandomEmail();
        String username = generateRandomUsername();
        String password = generateRandomPassword();

        System.setIn(createInputStream(email, username, password));
        authService.register(new java.util.Scanner(System.in));

        System.setIn(createInputStream(email, password));
        User loggedInUser = authService.login(new java.util.Scanner(System.in));

        // Assert
        assertNotNull(loggedInUser, "Login should be successful for valid credentials.");
        assertEquals(email, loggedInUser.getEmail(), "Logged-in user email should match.");
        assertEquals(username, loggedInUser.getUsername(), "Logged-in user username should match.");
    }

    @Test
    void testLoginInvalidCredentials() {
        // Arrange
        String email = generateRandomEmail();
        String password = generateRandomPassword();

        System.setIn(createInputStream(email, password));
        User loggedInUser = authService.login(new java.util.Scanner(System.in));

        // Assert
        assertNull(loggedInUser, "Login should fail for invalid credentials.");
    }
}