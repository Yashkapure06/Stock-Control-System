package testcases.whitebox.services;



import java.util.Optional;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import models.User;
import repositories.UserRepository;
import services.AuthService;
import utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class AuthServiceTest {

    private AuthService authService;
    private UserRepository userRepository;
    private final String testFilePath = "E:\\Eclipse\\Stock-Control-System\\src\\resources\\users_test.json"; // Test JSON path

    @BeforeEach
    void setup() {
        // Clear or create the test file before every test
        File testFile = new File(testFilePath);
        FileUtil.writeToFile(testFilePath, new ArrayList<User>()); // Write an empty list to the file

        // Override the repository to use the test file
        userRepository = new UserRepository() {
            @Override
            public String filePath() {
                return testFilePath;
            }
        };

        authService = new AuthService(userRepository);
    }

    @AfterEach
    void tearDown() {
        // Clean up the test file after each test
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
    @Test
    void testLoginWithValidCredentials() {
        User user = new User("test@example.com", "testUser", "password123");
        userRepository.saveUser(user);

        Scanner input = new Scanner("test@example.com\npassword123\n");
        User loggedInUser = authService.login(input);
        
        assertNotNull(loggedInUser, "User should log in successfully with valid credentials.");
        assertEquals("testUser", loggedInUser.getUsername());
    }

    @Test
    void testLoginWithInvalidEmail() {
        User user = new User( "test@example.com","testUser", "password123");
        userRepository.saveUser(user);

        Scanner input = new Scanner("wrong@example.com\npassword123\n");
        User loggedInUser = authService.login(input);

        assertNull(loggedInUser, "User should not log in with an invalid email.");
    }

    @Test
    void testLoginWithInvalidPassword() {
        User user = new User("test@example.com", "testUser", "password123");
        userRepository.saveUser(user);

        Scanner input = new Scanner("test@example.com\nwrongpassword\n");
        User loggedInUser = authService.login(input);

        assertNull(loggedInUser, "User should not log in with an invalid password.");
    }
    
//    @Test
//    void testRegisterDuplicateEmail() {
//        User existingUser = new User( "test@example.com","testUser", "password123");
//        userRepository.saveUser(existingUser);
//
//        Scanner input = new Scanner("duplicateUser\ntest@example.com\nnewPassword\n");
//        User newUser = authService.register(input);
//
//        assertNull(newUser.getEmail(), "User with a duplicate email should not be registered.");
//    }
    

    

    
}
