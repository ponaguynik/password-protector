package com.ponaguynik.passwordprotector.other;

import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.database.DBConnector;
import com.ponaguynik.passwordprotector.other.Alerts;
import javafx.application.Platform;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class MenuHelper {

    private static ResourceBundle res = ResourceBundle.getBundle("strings.main");

    public static void changeUser() {
        if (Alerts.showConfirm(res.getString("change.user"), res.getString("change.user.content"))) {
            try {
                SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.LOGIN);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public static boolean exit() {
        if (Alerts.showConfirm(res.getString("exit"), res.getString("exit.content"))) {
            DBConnector.close();
            Platform.exit();
            return true;
        } else return false;
    }

    public static void changeKeyword() {
        Stage stage = new Stage();
        stage.setTitle(res.getString("change.keyword.title"));
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            SceneSwitcher.set(stage, SceneSwitcher.Scenes.CHANGE_KEYWORD);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void deleteAccount() {
        boolean isConfirmed = Alerts.showConfirm(res.getString("delete.account"), res.getString("delete.account.content"));
        if (isConfirmed) {
            Stage stage = new Stage();
            stage.setTitle(res.getString("delete.account.title"));
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
