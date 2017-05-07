package com.ponaguynik.passwordprotector.util;

/**
 * The Validator class is used for validation of input data.
 */
public class Validator {

    private String s;

    public Validator() {

    }

    public Validator(String s) {
        this.s = s;
    }


    /**
     * @return true if length of the string s greater than min.
     */
    public boolean lengthGreaterThanMin(int min) {
        return s.matches("^.{" + min + ",}$");
    }

    /**
     * @return true if length of the string s greater than min.
     */
    public boolean lengthLessThanMax(int max) {
        return s.matches("^.{0," + max + "}$");
    }

    /**
     * @return true if the string s doesn't contain spaces.
     */
    public boolean noSpaces() {
        return s.matches("(?=\\S+$).+");
    }

    /**
     * @return true if the string s contains all of these characters:
     * (0-9), (a-z), (A-Z) and (@_#()^!.,~%&:;/).
     * (0-9) means any digit of this range.
     */
    public boolean hasAllCharacters() {
        return s.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@_#()^!.,~%&:;/]).+$");
    }

    /**
     * @return true if the string s stars with an english letter.
     */
    public boolean startsWithLetter() {
        return s.matches("^[a-zA-Z].+$");
    }

    /**
     * @return true if the string s contains only permissible characters:
     * (a-z), (A-Z), (0-9), (@_#()^!.,~%%&:;/)
     */
    public boolean onlyPermissibleCharacters() {
        return s.matches("^[a-zA-Z0-9@_#()^!~%&:;/]+$");
    }

    /**
     * @return true if the string s contains only English letters and digits.
     */
    public boolean onlyLettersAndDigits() {
        return s.matches("^[a-zA-Z0-9]+$");
    }

    public String getString() {
        return s;
    }

    public void setString(String s) {
        this.s = s;
    }

}
