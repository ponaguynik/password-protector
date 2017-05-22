package com.ponaguynik.passwordprotector.controller.login;


import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.model.User;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginVerifier {

    private static ResourceBundle res = ResourceBundle.getBundle("strings.login");

    private LoginVerifier() {

    }

    /**
     * Verify user in the database.
     *
     * @return null if verified otherwise return error message.
     */
    public static String verify(User user) throws SQLException {
        if (user.getUsername().isEmpty())
            return String.format(res.getString("field.empty"), res.getString("username"));
        if (user.getKeyword().isEmpty())
            return String.format(res.getString("field.empty"), res.getString("keyword"));

        return DBWorker.verifyKeyword(user) ? null : res.getString("invalid");
    }
}
