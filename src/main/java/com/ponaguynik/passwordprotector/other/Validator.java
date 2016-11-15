package com.ponaguynik.passwordprotector.other;

public class Validator {

    private static Validator instance = new Validator();

    private Validator() {}

    public static Validator getInstance() {
        return instance;
    }

    private String s;

    public boolean lengthGreaterThanMin(int min) {
        return s.matches("^.{" + min + ",}$");
    }

    public boolean lengthLessThanMax(int max) {
        return s.matches("^.{0," + max + "}$");
    }

    public boolean noSpaces() {
        return s.matches("(?=\\S+$).+");
    }

    public boolean hasAllCharacters() {
        return s.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@_#()^!.,~%&:;/]).+$");
    }

    public boolean startsWithLetter() {
        return s.matches("^[a-zA-Z].+$");
    }

    public boolean onlyPermissibleCharacters() {
        return s.matches("^[a-zA-Z0-9@_#()^!~%&:;/]+$");
    }

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
