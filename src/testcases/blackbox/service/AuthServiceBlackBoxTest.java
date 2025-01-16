package testcases.blackbox.service;

import models.User;
import repositories.UserRepository;
import services.AuthService;
import com.fasterxml.jackson.core.type.TypeReference; 
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceBlackBoxTest {



    @Test
    public void testRegisterNewEmail() throws IOException {
        UserRepository userRepository = new UserRepository();
        AuthService authService = new AuthService(userRepository);

        // Simulate user input for registration
        Scanner input = new Scanner("newuser@example.com\nnewUser\npassword123\n");

        User registeredUser = authService.register(input);
        assertNotNull(registeredUser, "User should be registered successfully");
        assertEquals("newuser@example.com", registeredUser.getEmail(), "Email should match");

        // Clean up: Remove the user from the JSON file
        cleanUpUserFromJson("newuser@example.com");
    }

    private void cleanUpUserFromJson(String email) throws IOException {
        // Path to the user JSON file (adjust if necessary)
        String usersFilePath = "src/resources/users.json";

        // Initialize ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Read the current user data
        File file = new File(usersFilePath);
        if (!file.exists()) {
            throw new FileNotFoundException("Users JSON file not found: " + usersFilePath);
        }

        // Load existing users from JSON
        List<Map<String, Object>> users = objectMapper.readValue(file, new TypeReference<List<Map<String, Object>>>() {});

        // Remove the user with the matching email
        users.removeIf(user -> email.equals(user.get("email")));

        // Write updated user data back to the JSON file
        objectMapper.writeValue(file, users);
    }




    @Test
    public void testRegisterExistingEmail() {

        UserRepository userRepository = new UserRepository();
        AuthService authService = new AuthService(userRepository);

        Scanner input = new Scanner("existinguser@example.com\nexistingUser\npassword123\n");

        // Register user for the first time
        authService.register(input);

        // Try to register again with the same email
        input = new Scanner("existinguser@example.com\nanotherUser\npassword456\n");
        User registeredUser = authService.register(input);
        assertNull(registeredUser, "Registration should fail for existing email");
    }

    @Test
    public void testRegisterEmptyFields() {

        UserRepository userRepository = new UserRepository();
        AuthService authService = new AuthService(userRepository);

        Scanner input = new Scanner("\nnewUser\npassword123\n");
        User registeredUser = authService.register(input);
        assertNull(registeredUser, "Registration should fail for empty email");

        input = new Scanner("newuser2@example.com\n\npassword123\n");
        registeredUser = authService.register(input);
        assertNull(registeredUser, "Registration should fail for empty username");

        input = new Scanner("newuser3@example.com\nnewUser3\n\n");
        registeredUser = authService.register(input);
        assertNull(registeredUser, "Registration should fail for empty password");

    }

    @Test
    public void testLoginSuccessful() {
        UserRepository userRepository = new UserRepository();
        AuthService authService = new AuthService(userRepository);

        Scanner registerInput = new Scanner("loginuser@example.com\nloginUser\npassword123\n");
        authService.register(registerInput);

        Scanner loginInput = new Scanner("loginuser@example.com\npassword123\n");
        User loggedInUser = authService.login(loginInput);
        assertNotNull(loggedInUser, "Login should be successful");
        assertEquals("loginuser@example.com", loggedInUser.getEmail(), "Email should match");

    }

    @Test
    public void testLoginUnsuccessful() {

        UserRepository userRepository = new UserRepository();
        AuthService authService = new AuthService(userRepository);

        Scanner registerInput = new Scanner("loginuser@example.com\nloginUser\npassword123\n");
        authService.register(registerInput);

        Scanner loginInput = new Scanner("loginuser@example.com\nwrongpassword\n");
        User loggedInUser = authService.login(loginInput);
        assertNull(loggedInUser, "Login should fail with incorrect password");

        loginInput = new Scanner("wronguser@example.com\npassword123\n");
        loggedInUser = authService.login(loginInput);
        assertNull(loggedInUser, "Login should fail with incorrect email");

    }

}
