package com.ponaguynik.passwordprotector;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    public enum Scenes {
        LOGIN, CHECK_IN, MAIN
    }
    private static Stage primaryStage = PasswordProtector.primaryStage;

    private static FXMLLoader loginLoader = new FXMLLoader(PasswordProtector.class.getResource("scenes/login_scene/login.fxml"));
    private static FXMLLoader checkinLoader = new FXMLLoader(PasswordProtector.class.getResource("scenes/checkin_scene/check-in.fxml"));
    private static FXMLLoader mainLoader = new FXMLLoader(PasswordProtector.class.getResource("scenes/main_scene/main.fxml"));

    public static void set(Scenes scene) throws IOException {
        Parent root;
        switch (scene) {
            case LOGIN:
                primaryStage.setResizable(false);
                root = loginLoader.load();
                break;
            case CHECK_IN:
                primaryStage.setResizable(false);
                root = checkinLoader.load();
                break;
            case MAIN:
                root = mainLoader.load();
                primaryStage.setResizable(true);
                break;
            default:
                throw new IOException();
        }
        primaryStage.hide();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static Object getController(Scenes scene) {
        switch (scene) {
            case LOGIN:
                return loginLoader.getController();
            case CHECK_IN:
                return checkinLoader.getController();
            case MAIN:
                return mainLoader.getController();
            default:
                return null;
        }
    }
}
