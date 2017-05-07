package com.ponaguynik.passwordprotector.controller;


import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.exceptions.UserAlreadyExists;
import com.ponaguynik.passwordprotector.util.Password;

import java.sql.SQLException;

public class Registrar {

    public static void register(String username, String keyword) throws UserAlreadyExists, SQLException {
        if (DBWorker.userExists(username))
            throw new UserAlreadyExists("User with such username already exists");

        DBWorker.addUser(username, Password.getSaltedHash(keyword));
    }
}
