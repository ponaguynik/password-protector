package com.ponaguynik.passwordprotector.controller.login;


import com.ponaguynik.passwordprotector.database.DBWorker;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class Verifier {

    private static ResourceBundle res = ResourceBundle.getBundle("strings.login");

    public static String verify(String username, String keyword) throws SQLException {
        if (username.isEmpty())
            return res.getString("username.empty");
        if (keyword.isEmpty())
            return res.getString("keyword.empty");

        res.getString("welcome");

        return DBWorker.verifyKeyword(username, keyword) ? null : res.getString("invalid");
    }
}
