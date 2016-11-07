package com.ponaguynik.passwordprotector;

import com.ponaguynik.passwordprotector.database.*;
import com.ponaguynik.passwordprotector.other.Alerts;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class PasswordProtector extends Application {

    public static String currentUser = null;

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("PasswordProtector");
        DBConnector.makeConnection();
        PasswordProtector.primaryStage = primaryStage;
        SceneSwitcher.set(primaryStage, SceneSwitcher.Scenes.LOGIN);
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}