package com.ponaguynik.passwordprotector.other;

public class Validator {

    private String s;

    public Validator(String s) {
        this.s = s;
    }

    public boolean lengthGreaterThanProper() {
        return s.matches("^.{8,}$");
    }

    public boolean lengthLessThanProper() {
        return s.matches("^.{1,16}$");
    }

    public boolean noSpaces() {
        return s.matches("(?=\\S+$).+");
    }

    public boolean allCharacters() {
        return s.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@_#()^!~%&:;/]).+$");
    }

    public boolean startsWithLetter() {
        return s.matches("^[a-bA-Z].+$");
    }

    public boolean permissibleCharacters() {
        return s.matches("^[a-zA-Z0-9@_#()^!~%&:;/]+$");
    }
}
