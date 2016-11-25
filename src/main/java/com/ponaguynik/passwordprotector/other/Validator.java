package com.ponaguynik.passwordprotector.other;

/**
 * The Validator class is a singleton that is used for
 * validation of input data in fields.
 */

public class Validator {

    /**
     * Instance of the Validator class.
     */
    private static Validator instance = new Validator();

    /**
     * Private constructor.
     */
    private Validator() {}

    /**
     * @return instance of the Validator class.
     */
    public static Validator getInstance() {
        return instance;
    }

    /**
     * A string that will be checking.
     */
    private String s;

    /**
     * @return true if the length of the s string greater than min.
     */
    public boolean lengthGreaterThanMin(int min) {
        return s.matches("^.{" + min + ",}$");
    }

    /**
     * @return true if the length of the s string greater than min.
     */
    public boolean lengthLessThanMax(int max) {
        return s.matches("^.{0," + max + "}$");
    }

    /**
     * @return true if the s string doesn't contain spaces.
     */
    public boolean noSpaces() {
        return s.matches("(?=\\S+$).+");
    }

    /**
     * @return true if the s string contains all of these characters:
     * (0-9), (a-z), (A-Z) and (@_#()^!.,~%&:;/)
     */
    public boolean hasAllCharacters() {
        return s.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@_#()^!.,~%&:;/]).+$");
    }

    /**
     * @return true if the s string stars with a letter.
     */
    public boolean startsWithLetter() {
        return s.matches("^[a-zA-Z].+$");
    }

    /**
     * @return true if the s string contains only permissible characters:
     * (a-z), (A-Z), (0-9), (@_#()^!.,~%%&:;/)
     */
    public boolean onlyPermissibleCharacters() {
        return s.matches("^[a-zA-Z0-9@_#()^!~%&:;/]+$");
    }

    /**
     * @return true if the s string contains only English letters and digits.
     */
    public boolean onlyLettersAndDigits() {
        return s.matches("^[a-zA-Z0-9]+$");
    }

    /**
     * @return the s string.
     */
    public String getString() {
        return s;
    }

    /**
     * Set the s string.
     */
    public void setString(String s) {
        this.s = s;
    }

}
