package com.ponaguynik.passwordprotector.controller.register;


import com.ponaguynik.passwordprotector.util.Validator;

import java.util.ResourceBundle;

public class RegistrationValidator {

    private static ResourceBundle res = ResourceBundle.getBundle("strings.register");

    public static String[] validate(String username, String keyword, String confirmKeyword) {
        String[] msg = new String[2];
        if (username.isEmpty())
            msg[0] = res.getString("username.empty");
        else if (keyword.isEmpty())
            msg[0] = res.getString("keyword.empty");
        else if (confirmKeyword.isEmpty())
            msg[0] = res.getString("confirm.keyword.empty");

        if (msg[0] != null)
            return msg;

        Validator validator = new Validator(username);
        msg = validateUsername(validator, 4, 16);
        if (msg != null)
            return msg;

        validator.setString(keyword);
        msg = validateKeyword(validator, 8, 16);
        if (msg != null)
            return msg;

        msg = new String[2];
        if (!keyword.equals(confirmKeyword)) {
            msg[0] = res.getString("not.match");
            return msg;
        }

        return null;
    }

    private static String[] validateUsername(Validator validator,int min, int max) {
        String[] msg = new String[2];
        if (!validator.lengthGreaterThanMin(min)) {
            msg[0] = String.format(res.getString("too.short"), "Username");
            msg[1] = String.format(res.getString("too.short.content"), "Username", min);
        } else if (!validator.lengthLessThanMax(max)) {
            msg[0] = String.format(res.getString("too.long"), "Username");
            msg[1] = String.format(res.getString("too.long.content"), "Username", max);
        } else if (!validator.startsWithLetter()) {
            msg[0] = String.format(res.getString("start.letter"), "Username");
            msg[1] = String.format(res.getString("start.letter.content"), "Username");
        } else if (!validator.noSpaces()) {
            msg[0] = res.getString("space");
            msg[1] = String.format(res.getString("space.content"), "Username");
        } else if (!validator.onlyLettersAndDigits()) {
            msg[0] = res.getString("not.permissible");
            msg[1] = res.getString("not.permissible.content");
        } else if (!validator.onlyPermissibleCharacters()) {
            msg[0] = res.getString("not.permissible");
            msg[1] = String.format(res.getString("consist.of"), "Username");
        }

        return msg[0] == null ? null : msg;
    }

    private static String[] validateKeyword(Validator validator, int min, int max) {
        String[] msg = new String[2];
        if (!validator.lengthGreaterThanMin(min)) {
            msg[0] = String.format(res.getString("too.short"), "Keyword");
            msg[1] = String.format(res.getString("too.short.content"), "Keyword", min);
        } else if (!validator.lengthLessThanMax(max)) {
            msg[0] = String.format(res.getString("too.long"), "Keyword");
            msg[1] = String.format(res.getString("too.long.content"), "Keyword", max);
        } else if (!validator.noSpaces()) {
            msg[0] = res.getString("space");
            msg[1] = String.format(res.getString("space.content"), "Keyword");
        } else if (!validator.hasAllCharacters()) {
            msg[0] = res.getString("not.enough.characters");
            msg[1] = res.getString("not.enough.characters.content");
        } else if (!validator.onlyPermissibleCharacters()) {
            msg[0] = res.getString("not.permissible");
            msg[1] = String.format(res.getString("consist.of"), "Keyword");
        }

        return msg[0] == null ? null : msg;
    }
}
