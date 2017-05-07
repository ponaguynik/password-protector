package com.ponaguynik.passwordprotector.database;

/**
 * The abstract DBWorker class is used for working with the database.
 * It can add and delete users, update keyword, add, delete
 * and update data forms and verify keywords in the database.
 * It is completely implemented. There are only
 * static fields and methods.
 */

import com.ponaguynik.passwordprotector.util.Alerts;
import com.ponaguynik.passwordprotector.util.Password;
import com.ponaguynik.passwordprotector.scenes.main.DataForm;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBWorker {
    

    /**
     * Add user's username and keyword to a database.
     *
     */
    public static void addUser(String username, String keyword) {
        String query = String.format("INSERT INTO users (username, keyword) VALUES('%s', '%s')", username, keyword);
        execute(query);
    }

    /**
     * Update user's keyword by username.
     */
    public static void updateKeyword(String username, String keyword) {
        String query = String.format("UPDATE users SET keyword = '%s' WHERE username = '%s'", keyword, username);
        execute(query);
    }

    /**
     * Delete a user and its data by username.
     */
    public static void deleteUser(String username) {
        String query = String.format("DELETE FROM users WHERE username = '%s'", username);
        execute(query);
        query = String.format("DELETE FROM users_data WHERE username = '%s'", username);
        execute(query);
    }

    /**
     * Add a new empty data form to the database by username.
     */
    public static void addDataForm(String username) {
        String query = String.format("INSERT INTO users_data (username, title, login, password) " +
                "VALUES('%s', '%s', '%s', '%s')", username, "", "", "");
        execute(query);
    }

    /**
     * Delete a data form by id.
     */
    public static void deleteDataForm(int id) {
        String query = String.format("DELETE FROM users_data WHERE id = %d", id);
        execute(query);
    }

    /**
     * Compare keyword with user's keyword by username.
     * @return true if keyword and user's keyword matched.
     */
    public static boolean verifyKeyword(String username, String keyword) {
        if (keyword == null || keyword.isEmpty())
            return false;
        String query = String.format("SELECT keyword FROM users WHERE username = '%s'", username);
        ArrayList<Map<String, Object>> result = executeQuery(query);

        return Password.check(keyword, (String) result.get(0).get("keyword"));
    }

    /**
     * Check whether user, with such username, exists.
     */
    public static boolean userExists(String username) {
        String query = String.format("SELECT keyword FROM users WHERE username = '%s'", username);
        return executeQuery(query) != null;
    }

    /**
     * Update a data form in the database.
     * Encrypt password of the data form.
     *
     * @param dataForm is object that contains id, title, login and password.
     */
    public static void updateDataForm(DataForm dataForm) {
        String query = String.format("UPDATE users_data SET title = '%s', login = '%s', password = '%s' WHERE id = %d",
                dataForm.getTitle(), dataForm.getLogin(), Password.encrypt(dataForm.getPassword()), dataForm.getDFId());
        execute(query);
    }

    /**
     * @return ArrayList of DataForm objects of a user by username.
     */
    public static ArrayList<DataForm> getAllDataForms(String username) {
        ArrayList<DataForm> list = new ArrayList<>();
        String query = String.format("SELECT * FROM users_data WHERE username = '%s'", username);
        ArrayList<Map<String, Object>> result = executeQuery(query);
        if (result == null)
            return null;
        DataForm dataForm;
        for (Map<String, Object> map : result) {
            dataForm = new DataForm((Integer) map.get("id"), (String) map.get("title"), (String) map.get("login"),
                    Password.decrypt((String) map.get("password")));
            dataForm.setEditMode(false);
            list.add(dataForm);
        }

        return list;
    }

    private static void execute(String query) {
        Connection connection = DBConnector.getConnection();

        try (
                Statement statement = connection.createStatement()
        ) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            Alerts.showError(e.getMessage());
            System.exit(1);
        }
    }

    private static ArrayList<Map<String,Object>> executeQuery(String query) {
        Connection connection = DBConnector.getConnection();

        ArrayList<Map<String, Object>> results = null;
        try (
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query)
        ) {
            results = convertToListOfMaps(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            Alerts.showError(e.getMessage());
            System.exit(1);
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
