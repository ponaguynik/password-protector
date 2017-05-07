package com.ponaguynik.passwordprotector.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordTest {

    private String testString, hashTestString;

    @Before
    public void setUp() throws Exception {
        testString = "testString";
        hashTestString = Password.getSaltedHash(testString);
    }

    @Test
    public void testGetSaltedHash() throws Exception {
        assertTrue(hashTestString.contains("$"));
    }

    @Test
    public void testCheck() throws Exception {
        assertTrue(Password.check(testString, hashTestString));
        assertFalse(Password.check(testString + "wrong", hashTestString));
    }

    @Test
    public void testEncrypt() throws Exception {
        String encrypted = Password.encrypt(testString);
        assertFalse(encrypted.equals(testString));
    }

    @Test
    public void testDecrypt() throws Exception {
        String encrypted = Password.encrypt(testString);
        assertTrue(testString.equals(Password.decrypt(encrypted)));
    }
}