package com.ponaguynik.passwordprotector.util;

import java.util.ResourceBundle;

/**
 * The Validator class is used for validation of input data.
 */
public class Validator {

    private static final ResourceBundle RES = ResourceBundle.getBundle("strings.register");

    private Validator() {

    }

    /**
     * @param target is a name of the field.
     * @param value is a string that has to been validated.
     * @return null if validated otherwise return error message.
     */
    public static String[] validateAsUsername(String target, String value) {
        String[] msg = validateGeneral(target, value, 4, 16);
        if (msg != null)
            return msg;

        msg = new String[2];
        if (!onlyLettersAndDigits(value)) {
            msg[0] = RES.getString("not.permissible");
            msg[1] = String.format(RES.getString("not.permissible.content"), target);
        } else if (!startsWithLetter(value)) {
            msg[0] = String.format(RES.getString("start.letter"), target);
            msg[1] = String.format(RES.getString("start.letter.content"), target);
        }

        return msg[0] == null ? null : msg;
    }

    public static String[] validateAsKeyword(String target, String value) {
        String[] msg = validateGeneral(target, value, 8, 16);
        if (msg != null)
            return msg;

        msg = new String[2];
        if (!onlyPermissibleCharacters(value)) {
            msg[0] = RES.getString("not.permissible");
            msg[1] = String.format(RES.getString("consist.of"), target);
        } else if (!hasAllCharacters(value)) {
            msg[0] = RES.getString("not.enough.characters");
            msg[1] = String.format(RES.getString("not.enough.characters.content"), target);
        }

        return msg[0] == null ? null : msg;
    }

    private static String[] validateGeneral(String target, String value, int min, int max) {
        String[] msg = new String[2];

        if (value == null || value.isEmpty()) {
            msg[0] = String.format(RES.getString("field.empty"), target);
        } else if (!lengthGreaterThanMin(value, min)) {
            msg[0] = String.format(RES.getString("too.short"), target);
            msg[1] = String.format(RES.getString("too.short.content"), target, min);
        } else if (!lengthLessThanMax(value, max)) {
            msg[0] = String.format(RES.getString("too.long"), target);
            msg[1] = String.format(RES.getString("too.long.content"), target, max);
        }  else if (!noSpaces(value)) {
            msg[0] = RES.getString("space");
            msg[1] = String.format(RES.getString("space.content"), target);
        }

        return msg[0] == null ? null : msg;
    }

    /**
     * @return true if length of the string s greater than min.
     */
    private static boolean lengthGreaterThanMin(String s, int min) {
        return s.matches("^.{" + min + ",}$");
    }

    /**
     * @return true if length of the string s greater than min.
     */
    private static boolean lengthLessThanMax(String s, int max) {
        return s.matches("^.{0," + max + "}$");
    }

    /**
     * @return true if the string s doesn't contain spaces.
     */
    private static boolean noSpaces(String s) {
        return s.matches("(?=\\S+$).+");
    }

    /**
     * @return true if the string s contains all of these characters:
     * (0-9), (a-z), (A-Z) and (@_#()^!.,~%&:;/).
     * (0-9) means any digit of this range.
     */
    private static boolean hasAllCharacters(String s) {
        return s.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@_#()^!.,~%&:;/]).+$");
    }

    /**
     * @return true if the string s stars with an english letter.
     */
    private static boolean startsWithLetter(String s) {
        return s.matches("^[a-zA-Z].+$");
    }

    /**
     * @return true if the string s contains only permissible characters:
     * (a-z), (A-Z), (0-9), (@_#()^!.,~%%&:;/)
     */
    private static boolean onlyPermissibleCharacters(String s) {
        return s.matches("^[a-zA-Z0-9@_#()^!~%&:;/]+$");
    }

    /**
     * @return true if the string s contains only English letters and digits.
     */
    private static boolean onlyLettersAndDigits(String s) {
        return s.matches("^[a-zA-Z0-9]+$");
    }

}
