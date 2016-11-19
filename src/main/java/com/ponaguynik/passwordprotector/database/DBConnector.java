package com.ponaguynik.passwordprotector.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnector {

    private static final String URL = "jdbc:sqlite:./users_data.db";
    private static Connection connection;

    public static void loadDatabase() {
        if (new File("./database.db").exists()) {
            connect();
        } else createDB();
        System.out.println("The connection to the database has been established!");
    }

    private static void connect() {
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

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

    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void execute(String query) {
        try {
            connection.createStatement().execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static ResultSet executeQuery(String query) {
        ResultSet rs = null;
        try {
            rs = connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
