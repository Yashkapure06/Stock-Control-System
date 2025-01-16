package testcases.whitebox.utils;


import utils.ValidationUtil;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {
    @Test
    void testIsValidEmail() {
        assertTrue(ValidationUtil.isValidEmail("test@example.com"));
        assertFalse(ValidationUtil.isValidEmail("invalid-email"));
        assertFalse(ValidationUtil.isValidEmail("test@.com"));
    }

    @Test
    void testIsValidPassword() {
        assertTrue(ValidationUtil.isValidPassword("StrongPass1!"));
        assertFalse(ValidationUtil.isValidPassword("short"));
        assertFalse(ValidationUtil.isValidPassword("NoSpecialChar123"));
    }

    @Test
    void testIsValidUsername() {
        assertTrue(ValidationUtil.isValidUsername("ValidUsername"));
        assertFalse(ValidationUtil.isValidUsername("Valid Username"));
        assertFalse(ValidationUtil.isValidUsername(""));
    }
}
