package com.ponaguynik.passwordprotector.other;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatorTest {

    private Validator validator;

    @Before
    public void setUp() throws Exception {
        validator = new Validator();
    }

    @Test
    public void testLengthGreaterThanMin() throws Exception {
        validator.setString("123456");
        assertTrue(validator.lengthGreaterThanMin(5));
        validator.setString("1234");
        assertFalse(validator.lengthGreaterThanMin(5));
    }

    @Test
    public void testLengthLessThanMax() throws Exception {
        validator.setString("123");
        assertTrue(validator.lengthLessThanMax(5));
        validator.setString("123456");
        assertFalse(validator.lengthLessThanMax(5));
    }

    @Test
    public void testNoSpaces() throws Exception {
        validator.setString("test_string");
        assertTrue(validator.noSpaces());
        validator.setString("test string");
        assertFalse(validator.noSpaces());
    }

    @Test
    public void testHasAllCharacters() throws Exception {
        validator.setString("Test1234@");
        assertTrue(validator.hasAllCharacters());
        validator.setString("test1");
        assertFalse(validator.hasAllCharacters());
    }

    @Test
    public void testStartsWithLetter() throws Exception {
        validator.setString("test1");
        assertTrue(validator.startsWithLetter());
        validator.setString("1test");
        assertFalse(validator.startsWithLetter());
    }

    @Test
    public void testOnlyPermissibleCharacters() throws Exception {
        validator.setString("Test1@");
        assertTrue(validator.onlyPermissibleCharacters());
        validator.setString("Русский1");
        assertFalse(validator.onlyPermissibleCharacters());
    }

    @Test
    public void testOnlyLettersAndDigits() throws Exception {
        validator.setString("test1234");
        assertTrue(validator.onlyLettersAndDigits());
        validator.setString("test1@");
        assertFalse(validator.onlyLettersAndDigits());
    }
}