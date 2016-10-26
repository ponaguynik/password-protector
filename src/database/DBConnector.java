package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnector {

    private static final String URL = "jdbc:sqlite:src/database/database.db";
    private static Connection connection;

    public static void makeConnection() {
        if (!new File("src/database/database.db").exists()) {
            createDB();
        } else {
            connect();
        }
        System.out.println("The connection has been successfully established!");
    }

    private static void connect() {
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
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
                "    username TEXT NOT NULL,\n" +
                "    title TEXT,\n" +
                "    login TEXT,\n" +
                "    password TEXT,\n" +
                "    CONSTRAINT username_fk FOREIGN KEY (username) REFERENCES users (username)\n" +
                ");");
        System.out.println("A database has been successfully created!");
    }

    static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static Connection getConnection() {
        return connection;
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
