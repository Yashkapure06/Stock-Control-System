package testcases.blackbox.repositories;

import models.User;
import repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

public class UserRepositoryBlackBoxTest {

    @Test
    public void testGetAllUsers() {
        UserRepository userRepository = new UserRepository();

        List<User> users = userRepository.getAllUsers();
        assertNotNull(users, "getAllUsers should not return null");
    }

    @Test
    public void testSaveUser() {
        UserRepository userRepository = new UserRepository();
        User user = new User("test@example.com", "testUser", "testPassword");

        userRepository.saveUser(user);

        List<User> users = userRepository.getAllUsers();
        boolean userSaved = users.stream().anyMatch(u -> u.getEmail().equals("test@example.com"));

        assertTrue(userSaved, "User should be saved successfully");
        System.out.println("saveUser test passed");
    }

    @Test
    public void testFindUserByEmail() {
        UserRepository userRepository = new UserRepository();
        String email = "test@example.com";
        User user = new User(email, "testUser", "testPassword");

        userRepository.saveUser(user);

        Optional<User> foundUser = userRepository.findUserByEmail(email);
        assertTrue(foundUser.isPresent(), "User should be found");
        assertEquals(email, foundUser.get().getEmail(), "User's email should match");

        // Case-insensitivity test
        String emailUpperCase = "TEST@EXAMPLE.COM";
        Optional<User> foundUserUpperCase = userRepository.findUserByEmail(emailUpperCase);

        assertTrue(foundUserUpperCase.isPresent(), "User should be found (case-insensitivity test)");
        assertEquals(email.toLowerCase(), foundUserUpperCase.get().getEmail().toLowerCase(), "User's email should match (case-insensitivity test)");

        System.out.println("findUserByEmail test passed");
    }
}
