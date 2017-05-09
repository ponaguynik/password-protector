package com.ponaguynik.passwordprotector.controller.register;


import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.exceptions.UserAlreadyExists;
import com.ponaguynik.passwordprotector.model.User;
import com.ponaguynik.passwordprotector.util.Password;

import java.sql.SQLException;

public class Registrar {

    public static void register(User user) throws UserAlreadyExists, SQLException {
        if (DBWorker.userExists(user))
            throw new UserAlreadyExists("User with such username already exists");

        DBWorker.addUser(user);
    }
}
