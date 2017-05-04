package com.ponaguynik.passwordprotector.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The abstract DBConnector class is used for creating a new database and connecting
 * to it. It is completely implemented. There are only
 * static fields and methods.
 */

public abstract class DBConnector {

    /**
     * URL to a database file.
     */
    private static final String URL = "jdbc:sqlite:./database.db";
    /**
     * Connection a the database.
     */
    private static Connection connection;

    /**
     * Make connection to a database or, if the database
     * does not exist, create a new database and initialize it.
     */
    public static boolean loadDatabase() {
        if (new File("./database.db").exists()) {
            connect();
        } else
            createDB();
        System.out.println("The connection to the database has been established!");
        return connection != null;
    }

    /**
     * Make connection to a database by URL.
     * Catch an SQLException: print stack trace and exit with 1.
     */
    private static void connect() {
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Connection to a database failed");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Create a new database and initialize it.
     * In the database create "users" and "users_data" tables.
     * "users" table contains username (primary key) and keyword fields.
     * "users_data" table contains id (primary key), username (reference
     * to the "username" field in "users" table), title, login and password fields.
     */
    private static void createDB() {
        connect();

        execute("CREATE TABLE users\n" +
                "(\n" +
                "    username TEXT PRIMARY KEY,\n" +
                "    keyword TEXT NOT NULL\n" +
                ");");
        execute("CREATE TABLE users_data\n" +
                "(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    username TEXT NOT NULL,\n" +
                "    title TEXT,\n" +
                "    login TEXT,\n" +
                "    password TEXT,\n" +
                "    CONSTRAINT username_fk FOREIGN KEY (username) REFERENCES users (username)\n" +
                ");");
        System.out.println("A new database has been successfully created!");
    }

    /**
     * Close connection to the database.
     */
    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Execute the given SQL statement.
     * Catch an SQLException: print stack trace and exit with 1.
     */
    static void execute(String query) {
        try {
            connection.createStatement().execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute the given SQL statement and return a ResultSet object.
     * Catch an SQLException: print stack trace and exit with 1.
     */
    static ResultSet executeQuery(String query) {
        ResultSet rs = null;
        try {
            rs = connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return rs;
    }
}
