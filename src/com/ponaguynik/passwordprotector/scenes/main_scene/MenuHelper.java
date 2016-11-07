package com.ponaguynik.passwordprotector.scenes.main_scene;

import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.database.DBConnector;
import com.ponaguynik.passwordprotector.other.Alerts;
import javafx.application.Platform;

import java.io.IOException;

public class MenuHelper {

    public static void changeUser() {
        if (Alerts.showConfirm("Are you sure you want to change account?", "All unsaved data will be lost.")) {
            try {
                SceneSwitcher.set(SceneSwitcher.Scenes.LOGIN);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public static boolean exit() {
        if (Alerts.showConfirm("Are you sure you want to exit?", "All unsaved data will be lost.")) {
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

    }

    public static void deleteAccount() {

    }

    public static void about() {

    }
}
