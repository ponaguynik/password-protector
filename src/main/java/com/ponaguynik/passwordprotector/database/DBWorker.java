package com.ponaguynik.passwordprotector.database;

/**
 * The abstract DBWorker class is used for working with the database.
 * It can add and delete users, update keyword, add, delete
 * and update data forms and verify keywords in the database.
 * It is completely implemented. It has got only
 * static fields and methods.
 */

import com.ponaguynik.passwordprotector.other.Alerts;
import com.ponaguynik.passwordprotector.other.Password;
import com.ponaguynik.passwordprotector.scenes.main_scene.DataForm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class DBWorker {

    /**
     * Add user's username and keyword to the database.
     *
     */
    public static void addUser(String username, String keyword) {
        String query = String.format("INSERT INTO users (username, keyword) VALUES('%s', '%s')", username, keyword);
        DBConnector.execute(query);
    }

    /**
     * Update user's keyword by username.
     */
    public static void updateKeyword(String username, String keyword) {
        String query = String.format("UPDATE users SET keyword = '%s' WHERE username = '%s'", keyword, username);
        DBConnector.execute(query);
    }

    /**
     * Delete a user and its data by username.
     */
    public static void deleteUser(String username) {
        String query = String.format("DELETE FROM users WHERE username = '%s'", username);
        DBConnector.execute(query);
        query = String.format("DELETE FROM users_data WHERE username = '%s'", username);
        DBConnector.execute(query);
    }

    /**
     * Add a new empty data form to the database by username.
     */
    public static void addDataForm(String username) {
        String query = String.format("INSERT INTO users_data (username, title, login, password) " +
                "VALUES('%s', '%s', '%s', '%s')", username, "", "", "");
        DBConnector.execute(query);
    }

    /**
     * Delete a data form by its id.
     */
    public static void deleteDataForm(int id) {
        String query = String.format("DELETE FROM users_data WHERE id = %d", id);
        DBConnector.execute(query);
    }

    /**
     * Compare keyword with user's keyword by username.
     * @return true if keyword and user's keyword matched.
     */
    public static boolean verifyKeyword(String username, String keyword) {
        if (keyword == null || keyword.isEmpty())
            return false;
        String query = String.format("SELECT keyword FROM users WHERE username = '%s'", username);
        ResultSet rs = DBConnector.executeQuery(query);

        try {
            return Password.check(keyword, rs.getString("keyword"));
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Check whether user with such username exists.
     */
    public static boolean userExists(String username) {
        String query = String.format("SELECT keyword FROM users WHERE username = '%s'", username);
        try {
            DBConnector.executeQuery(query).getString("keyword");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Update a data form in the database.
     * Encrypt password of the data form.
     *
     * @param dataForm is object that contains its id, title, login and password.
     */
    public static void updateDataForm(DataForm dataForm) {
        String query = String.format("UPDATE users_data SET title = '%s', login = '%s', password = '%s' WHERE id = %d",
                dataForm.getTitle(), dataForm.getLogin(), Password.encrypt(dataForm.getPassword()), dataForm.getDFId());
        DBConnector.execute(query);
    }

    /**
     * @return ArrayList of DataForm objects of a user by username.
     */
    public static ArrayList<DataForm> getAllDataForms(String username) {
        ArrayList<DataForm> list = new ArrayList<>();
        String query = String.format("SELECT * FROM users_data WHERE username = '%s'", username);
        ResultSet rs = DBConnector.executeQuery(query);
        DataForm dataForm;
        try {
            while (rs.next()) {
                dataForm = new DataForm(rs.getInt("id"), rs.getString("title"), rs.getString("login"),
                        Password.decrypt(rs.getString("password")));
                dataForm.setEditMode(false);
                list.add(dataForm);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showError("Database error!");
            System.exit(1);
        }
        return list;
    }
}
