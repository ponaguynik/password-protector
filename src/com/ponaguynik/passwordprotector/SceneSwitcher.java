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

    public static void set(Scenes scene) throws IOException {
        Parent root;
        switch (scene) {
            case LOGIN:
                root = FXMLLoader.load(PasswordProtector.class.getResource("scenes/login_scene/login.fxml"));
                break;
            case CHECK_IN:
                //noinspection SpellCheckingInspection
                root = FXMLLoader.load(PasswordProtector.class.getResource("scenes/checkin_scene/check-in.fxml"));
                break;
            case MAIN:
                root = FXMLLoader.load(PasswordProtector.class.getResource("scenes/main_scene/main.fxml"));
                break;
            default:
                throw new IOException();
        }
        primaryStage.hide();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
