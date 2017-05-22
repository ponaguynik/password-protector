package com.ponaguynik.passwordprotector.util;

import org.junit.Test;

import static org.junit.Assert.*;


public class PasswordTest {

    @Test
    public void check() throws Exception {
        String correct = "correct";
        String correctHash = Password.getSaltedHash(correct);
        assertTrue(Password.check("correct", correctHash));
        String wrong = "wrong";
        assertFalse(Password.check(wrong, correctHash));
    }

    @Test
    public void encryptAndDecrypt() throws Exception {
        String test = "test";
        String encryptedTest = Password.encrypt(test);
        assertNotEquals(test, encryptedTest);
        String decryptedTest = Password.decrypt(encryptedTest);
        assertEquals(test, decryptedTest);
    }

}