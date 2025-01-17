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

        Scanner input = new Scanner("newuser@example.com\nnewUser\npassword123\n");

        User registeredUser = authService.register(input);
        assertNull(registeredUser, "Invalid email or password. Please try again");

    }

    private void cleanUpUserFromJson(String email) throws IOException {
        String usersFilePath = "src/resources/users.json";

        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File(usersFilePath);
        if (!file.exists()) {
            throw new FileNotFoundException("Users JSON file not found: " + usersFilePath);
        }

        List<Map<String, Object>> users = objectMapper.readValue(file, new TypeReference<List<Map<String, Object>>>() {});

        users.removeIf(user -> email.equals(user.get("email")));

        objectMapper.writeValue(file, users);
    }



    @Test
    public void testRegisterExistingEmail() {

        UserRepository userRepository = new UserRepository();
        AuthService authService = new AuthService(userRepository);

        Scanner input = new Scanner("existinguser@example.com\nexistingUser\npassword123\n");

        authService.register(input);

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
        assertNull(registeredUser, "Invalid email or password.");

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