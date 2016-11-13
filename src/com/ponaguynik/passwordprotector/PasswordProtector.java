package com.ponaguynik.passwordprotector;

import com.ponaguynik.passwordprotector.database.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class PasswordProtector extends Application {

    public static final String PATH =  "com.ponaguynik.passwordprotector.res.strings.";

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