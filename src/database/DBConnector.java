package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static final String URL = "jdbc:sqlite:src/database/database.db";
    private static Connection connection;

    static void makeConnection() {
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

        executeQuery("CREATE TABLE users\n" +
                "(\n" +
                "    id INT PRIMARY KEY,\n" +
                "    username TEXT NOT NULL,\n" +
                "    keyword TEXT NOT NULL\n" +
                ");\n" +
                "CREATE UNIQUE INDEX users_username_uindex ON users (username);");
        executeQuery("CREATE TABLE users_data\n" +
                "(\n" +
                "    id INT PRIMARY KEY,\n" +
                "    user_id INT NOT NULL,\n" +
                "    title TEXT,\n" +
                "    login TEXT,\n" +
                "    password TEXT,\n" +
                "    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id)\n" +
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

    static void executeQuery(String query) {
        try {
            connection.createStatement().execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        makeConnection();
    }
}
