package database;

import scenes.main_scene.DataForm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBWorker {

    public static void addUser(String username, String keyword) {
        String query = String.format("INSERT INTO users (username, keyword) VALUES('%s', '%s')", username, keyword);
        DBConnector.execute(query);
    }

    public static void addDataForm(String username, DataForm df) {
        String query = String.format("INSERT INTO users_data (username, title, login, password) " +
                "VALUES('%s', '%s', '%s', '%s')", username, df.getTitle(), df.getLogin(), df.getPassword());
        DBConnector.execute(query);
    }

    public static ArrayList<DataForm> getAllDataForms(String username) {
        ArrayList<DataForm> list = new ArrayList<>();
        String query = String.format("SELECT * FROM users_data WHERE username = '%s'", username);
        ResultSet rs = DBConnector.executeQuery(query);
        DataForm dfc;
        try {
            while (rs.next()) {
                dfc = new DataForm();
                dfc.setEditMode(false);
                dfc.setData(rs.getString("title"), rs.getString("login"), rs.getString("password"));
                list.add(dfc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
