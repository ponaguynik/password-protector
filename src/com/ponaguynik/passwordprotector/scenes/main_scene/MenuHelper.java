package com.ponaguynik.passwordprotector.scenes.main_scene;

import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.database.DBConnector;
import com.ponaguynik.passwordprotector.other.Alerts;
import javafx.application.Platform;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuHelper {

    public static void changeUser() {
        if (Alerts.showConfirm("Are you sure you want to change account?", "All unsaved data will be lost")) {
            try {
                SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.LOGIN);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public static boolean exit() {
        if (Alerts.showConfirm("Are you sure you want to exit?", "All unsaved data will be lost")) {
            DBConnector.close();
            Platform.exit();
            return true;
        } else return false;
    }

    public static void english() {

    }

    public static void russian() {

    }

    public static void changeKeyword() {
        Stage stage = new Stage();
        stage.setTitle("Change keyword");
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            SceneSwitcher.set(stage, SceneSwitcher.Scenes.CHANGE_KEYWORD);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void deleteAccount() {
        boolean isConfirmed = Alerts.showConfirm("Are you sure you want to delete this account?", "All your data will be lost");
        if (isConfirmed) {
            Stage stage = new Stage();
            stage.setTitle("Delete account");
            stage.initModality(Modality.APPLICATION_MODAL);
            try {
                SceneSwitcher.set(stage, SceneSwitcher.Scenes.DELETE);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public static void about() {

    }
}
