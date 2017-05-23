package com.ponaguynik.passwordprotector.util;

import org.junit.Test;

import static org.junit.Assert.*;


public class EncryptorTest {

    @Test
    public void check() throws Exception {
        String s1 = "test";
        String s2 = Encryptor.getSaltedHash("test");
        assertTrue(Encryptor.check(s1, s2));
    }

}