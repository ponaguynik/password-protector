package com.ponaguynik.passwordprotector.other;

/**
 * The abstract MenuHelper class is used for handling
 * events of menu items on the Main scene. It is completely
 * implemented. There are only static fields and methods.
 */

import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.database.DBConnector;
import javafx.application.Platform;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public abstract class MenuHelper {

    /**
     * ResourceBundle object that contains strings of the
     * main.properties file.
     */
    private static ResourceBundle res = ResourceBundle.getBundle("strings.main");

    /**
     * Handle event of "Change user" menu item. Ask a user whether it wants to change account.
     * Set login scene if true.
     */
    public static void changeAccount() {
        if (Alerts.showConfirm(res.getString("change.account"), res.getString("change.account.content"))) {
            try {
                SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.LOGIN);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    /**
     * Handle event of "Exit" menu item. Ask a user whether it
     * wants to close the program.
     *
     * @return false if it has been pressed Cancel button.
     */
    public static boolean exit() {
        if (Alerts.showConfirm(res.getString("exit"), res.getString("exit.content"))) {
            DBConnector.close();
            Platform.exit();
            return true;
        }
        return false;
    }

    /**
     * Handle event of "Change keyword" menu item.
     * Show Change Keyword modal window.
     */
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

    /**
     * Handle event of "Delete account" menu item. Ask the user whether it wants
     * to delete the account. Show Delete modal window
     * if true.
     */
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

    /**
     * Handle event of "About account" menu item.
     */
    public static void about() {

    }
}
