package com.ponaguynik.passwordprotector.database;

import com.ponaguynik.passwordprotector.other.Alerts;
import com.ponaguynik.passwordprotector.other.Password;
import com.ponaguynik.passwordprotector.scenes.main_scene.DataForm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBWorker {

    public static void addUser(String username, String keyword) {
        String query = String.format("INSERT INTO users (username, keyword) VALUES('%s', '%s')",
                username, keyword);
        DBConnector.execute(query);
    }

    public static void addDataForm(String username) {
        String query = String.format("INSERT INTO users_data (username, title, login, password) " +
                "VALUES('%s', '%s', '%s', '%s')", username, "", "", "");
        DBConnector.execute(query);
    }

    public static boolean checkKeyword(String username, String keyword) throws Exception {
        String query = String.format("SELECT keyword FROM users WHERE username = '%s'", username);
        ResultSet rs = DBConnector.executeQuery(query);
        return Password.check(keyword, rs.getString("keyword"));
    }

    public static boolean userExists(String username) {
        String query = String.format("SELECT keyword FROM users WHERE username = '%s'", username);
        try {
            DBConnector.executeQuery(query).getString("keyword");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static void updateDataForm(DataForm dataForm) {
        String query = String.format("UPDATE users_data SET title = '%s', login = '%s', password = '%s' WHERE id = %d",
                dataForm.getTitle(), dataForm.getLogin(), Password.encrypt(dataForm.getPassword()), dataForm.getDFId());
        DBConnector.execute(query);
    }

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
