package com.example.gc_coffee.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {

    @Test
    void testInvalidEmailFormat() {
        // Invalid email format should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Email("invalid-email"));
    }

    @Test
    void testValidEmail() {
        // Valid email should be created successfully
        Email email = new Email("hello@gmail.com");
        assertEquals("hello@gmail.com", email.getAddress());
    }

    @Test
    void testEmailEquality() {
        // Two emails with the same address should be equal
        Email email1 = new Email("hello@gmail.com");
        Email email2 = new Email("hello@gmail.com");
        assertEquals(email1, email2);
    }

    @Test
    void testEmailNullAddress() {
        // Null email address should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Email(null));
    }

    @Test
    void testEmailLengthTooShort() {
        // Email address that is too short should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Email("a@b.c"));
    }

    @Test
    void testEmailLengthTooLong() {
        // Email address that is too long should throw IllegalArgumentException
        String longEmail = "a".repeat(51) + "@example.com";
        assertThrows(IllegalArgumentException.class, () -> new Email(longEmail));
    }
}
