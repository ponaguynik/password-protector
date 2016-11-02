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

    private static Scene loginScene;
    private static Scene checkinScene;
    private static Scene mainScene;

    public static void set(Scenes scene) throws IOException {
        primaryStage.hide();
        switch (scene) {
            case LOGIN:
                primaryStage.setResizable(false);
                if (loginScene == null)
                    loginScene = new Scene(loginLoader.load());
                primaryStage.setScene(loginScene);
                break;
            case CHECK_IN:
                primaryStage.setResizable(false);
                if (checkinScene == null)
                    checkinScene = new Scene(checkinLoader.load());
                primaryStage.setScene(checkinScene);
                break;
            case MAIN:
                primaryStage.setResizable(true);
                if (mainScene == null)
                    mainScene = new Scene(mainLoader.load());
                primaryStage.setScene(mainScene);
                break;
            default:
                throw new IOException();
        }
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
