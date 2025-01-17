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
    private final String testFilePath = "E:\\Eclipse\\Stock-Control-System\\src\\resources\\users_test.json"; 

    @BeforeEach
    void setup() {
        File testFile = new File(testFilePath);
        FileUtil.writeToFile(testFilePath, new ArrayList<User>()); 

        userRepository = new UserRepository() {
            public String filePath() {
                return testFilePath;
            }
        };

        authService = new AuthService(userRepository);
    }

    @AfterEach
    void tearDown() {
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }




    @Test
    void testRegisterWithDuplicateEmail() {
        User existingUser = new User("test@example.com", "existingUser", "password123");
        userRepository.saveUser(existingUser);

        Scanner input = new Scanner("test@example.com\nduplicateUser\nnewPassword\n");
        User registeredUser = authService.register(input);

        assertNull(registeredUser, "User with a duplicate email should not be registered.");
    }

    

    @Test
    void testRegisterWithEmptyUsername() {
        Scanner input = new Scanner("test@example.com\n\npassword123\n");
        User registeredUser = authService.register(input);

        assertNull(registeredUser, "User should not be registered with an empty username.");
    }

    @Test
    void testRegisterWithEmptyPassword() {
        Scanner input = new Scanner("test@example.com\ntestUser\n\n");
        User registeredUser = authService.register(input);

        assertNull(registeredUser, "User should not be registered with an empty password.");
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




}