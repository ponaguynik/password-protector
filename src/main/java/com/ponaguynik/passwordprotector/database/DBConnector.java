package com.ponaguynik.passwordprotector.database;

import com.ponaguynik.passwordprotector.util.Alerts;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

/**
 * The abstract DBConnector class is used for creating a new database and connecting
 * to it. It is completely implemented. There are only
 * static fields and methods.
 */

public class DBConnector {

    private static final String URL = "jdbc:sqlite:./database.db";

    private static Connection connection;

    /**
     * Private constructor because the class consist of only static methods.
     */
    private DBConnector() {

    }

    /**
     * Make connection to a database or, if the database
     * does not exist, create a new database and initialize it.
     */
    public static void loadDatabase() {
        if (Files.exists(Paths.get("./database.db"))) {
            connect();
        } else
            createDB();
        System.out.println("Connection to the database has been established!");
    }

    /**
     * Make connection to a database by URL.
     * Catch an SQLException: print stack trace and exit with 1.
     */
    private static void connect() {
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Connection to the database failed");
            e.printStackTrace();
            Alerts.showError(e.getMessage());
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

        try (
                Statement statement = connection.createStatement()
                ) {
            statement.execute("CREATE TABLE users" +
                    "(" +
                    "    username TEXT PRIMARY KEY," +
                    "    keyword TEXT NOT NULL\n" +
                    ");");
            statement.execute("CREATE TABLE users_data" +
                    "(" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "    username TEXT NOT NULL," +
                    "    title TEXT," +
                    "    login TEXT," +
                    "    password TEXT," +
                    "    CONSTRAINT username_fk FOREIGN KEY (username) REFERENCES users (username)" +
                    ");");
        } catch (SQLException e) {
            Alerts.showError(e.getMessage());
            System.exit(1);
        }
        System.out.println("A new database has been successfully created!");
    }

    /**
     * Close connection to the database.
     */
    public static void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    static Connection getConnection() {
        return connection;
    }
}
