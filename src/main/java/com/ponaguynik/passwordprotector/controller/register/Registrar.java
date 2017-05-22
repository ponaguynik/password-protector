package com.ponaguynik.passwordprotector.controller.register;


import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.exceptions.UserAlreadyExists;
import com.ponaguynik.passwordprotector.model.User;

import java.sql.SQLException;

public class Registrar {

    private Registrar() {

    }

    /**
     * Register a new user in the database.
     */
    public static void register(User user) throws UserAlreadyExists, SQLException {
        if (DBWorker.userExists(user))
            throw new UserAlreadyExists("User with such username already exists");

        DBWorker.addUser(user);
    }
}
