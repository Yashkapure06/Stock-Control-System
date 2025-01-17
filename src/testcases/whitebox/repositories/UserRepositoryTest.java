package testcases.whitebox.repositories;

import repositories.UserRepository;
import models.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
    }

    @AfterEach
    void tearDown() {
        File file = new File("E:\\Eclipse\\Stock-Control-System\\src\\resources\\users.json");

    }

    @Test
    void testUserDoesNotExist() {
        Optional<User> user = userRepository.findUserByEmail("nonexistent@example.com");
        assertFalse(user.isPresent());
    }


}