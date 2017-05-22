package com.ponaguynik.passwordprotector.database;


import com.ponaguynik.passwordprotector.model.DataForm;
import com.ponaguynik.passwordprotector.model.User;
import com.ponaguynik.passwordprotector.util.Password;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * The DBWorker class is used for working with the database.
 */
public class DBWorker {
    
    private DBWorker() {

    }

    public static void addUser(User user) throws SQLException {
        String query = String.format("INSERT INTO users (username, keyword) VALUES('%s', '%s')",
                user.getUsername(), user.getKeyword());
        execute(query);
    }

    public static void updateKeyword(User user) throws SQLException {
        String query = String.format("UPDATE users SET keyword = '%s' WHERE username = '%s'", user.getKeyword(), user.getUsername());
        execute(query);
    }

    public static void deleteUser(User user) throws SQLException {
        String query = String.format("DELETE FROM users WHERE username = '%s'", user.getUsername());
        execute(query);
        query = String.format("DELETE FROM users_data WHERE username = '%s'", user.getUsername());
        execute(query);
    }

    public static void addDataForm(User user) throws SQLException {
        String query = String.format("INSERT INTO users_data (username, title, login, password) " +
                "VALUES('%s', '%s', '%s', '%s')", user.getUsername(), "", "", "");
        execute(query);
    }

    public static void deleteDataForm(DataForm dataForm) throws SQLException {
        String query = String.format("DELETE FROM users_data WHERE id = %d", dataForm.getId());
        execute(query);
    }

    public static boolean verifyKeyword(User user) throws SQLException {
        if (user.getKeyword() == null || user.getKeyword().isEmpty())
            return false;
        String query = String.format("SELECT keyword FROM users WHERE username = '%s'", user.getUsername());
        ArrayList<Map<String, Object>> result = executeQuery(query);
        return result != null && Password.check(user.getKeyword(), (String) result.get(0).get("keyword"));
    }

    public static boolean userExists(User user) throws SQLException {
        String query = String.format("SELECT keyword FROM users WHERE username = '%s'", user.getUsername());
        return executeQuery(query) != null;
    }

    public static void updateDataForm(DataForm dataForm) throws SQLException {
        String query = String.format("UPDATE users_data SET title = '%s', login = '%s', password = '%s' WHERE id = %d",
                dataForm.getTitle(), dataForm.getLogin(), Password.encrypt(dataForm.getPassword()), dataForm.getId());
        execute(query);
    }

    /**
     * @return ArrayList of DataForm objects of the user.
     */
    public static ArrayList<DataForm> getAllDataForms(User user) throws SQLException {
        ArrayList<DataForm> list = new ArrayList<>();
        String query = String.format("SELECT * FROM users_data WHERE username = '%s'", user.getUsername());
        ArrayList<Map<String, Object>> result = executeQuery(query);
        if (result == null)
            return null;
        DataForm dataForm;
        for (Map<String, Object> map : result) {
            dataForm = new DataForm((Integer) map.get("id"), (String) map.get("title"), (String) map.get("login"),
                    Password.decrypt((String) map.get("password")));
            list.add(dataForm);
        }

        return list;
    }

    private static void execute(String query) throws SQLException {
        Connection connection = DBConnector.getConnection();

        try (
                Statement statement = connection.createStatement()
        ) {
            statement.execute(query);
        }
    }

    private static ArrayList<Map<String,Object>> executeQuery(String query) throws SQLException {
        Connection connection = DBConnector.getConnection();

        ArrayList<Map<String, Object>> results;
        try (
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query)
        ) {
            results = convertToListOfMaps(rs);
        }

        return results;
    }

    private static ArrayList<Map<String, Object>> convertToListOfMaps(ResultSet rs) throws SQLException {
        ArrayList<Map<String, Object>> result = new ArrayList<>();

        ResultSetMetaData rsMetaData = rs.getMetaData();
        while (rs.next()) {
            Map<String, Object> map = new HashMap<>();
            for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
                map.put(rsMetaData.getColumnName(i), rs.getObject(i));
            result.add(map);
        }
        if (result.isEmpty())
            return null;

        return result;
    }
}
