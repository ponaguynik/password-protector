package com.ponaguynik.passwordprotector;

import com.ponaguynik.passwordprotector.database.*;
import com.ponaguynik.passwordprotector.model.User;
import com.ponaguynik.passwordprotector.util.Alerts;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class PasswordProtector extends Application {

    public static User currentUser = null;

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("PasswordProtector");
        try {
            DBConnector.loadDatabase();
        } catch (SQLException e) {
            System.out.println("Connection to the database failed");
            e.printStackTrace();
            Alerts.showError(e.getMessage());
            System.exit(1);
        }
        PasswordProtector.primaryStage = primaryStage;
        SceneSwitcher.set(primaryStage, SceneSwitcher.Scenes.LOGIN);
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}