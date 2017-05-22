package com.ponaguynik.passwordprotector.database;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;


/**
 * The DBConnector class is used for creating a new database and connecting
 * to it.
 */
public class DBConnector {

    private static final String URL = "jdbc:sqlite:./database.db";
    private static Connection connection;

    private DBConnector() {

    }

    /**
     * Make connection to the database or, if the database
     * does not exist, create a new one and initialize it.
     */
    public static void loadDatabase() throws SQLException {
        if (Files.exists(Paths.get("./database.db"))) {
            connect();
        } else
            createDB();
        System.out.println("Connection to the database has been established!");
    }

    /**
     * Make connection to the database by URL.
     */
    private static void connect() throws SQLException {
        connection = DriverManager.getConnection(URL);
    }

    /**
     * Create a new database and initialize it.
     * In the database create "users" and "users_data" tables.
     */
    private static void createDB() throws SQLException {
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
        } catch (SQLException ignore) {}
    }

    static Connection getConnection() {
        return connection;
    }
}
