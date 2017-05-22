package com.ponaguynik.passwordprotector.controller.main;



import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.database.DBConnector;
import com.ponaguynik.passwordprotector.util.Alerts;
import javafx.application.Platform;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * The MenuHelper class is used for handling
 * events of menu items on the Main scene.
 */
public class MenuHelper {

    private static final ResourceBundle RES = ResourceBundle.getBundle("strings.main");

    private MenuHelper() {

    }

    /**
     * Handle event of "Change user" menu item. Ask a user whether it wants to change account.
     * Set login scene if true.
     */
    public static void changeAccount() {
        if (Alerts.showConfirm(RES.getString("change.account"), RES.getString("change.account.content")))
            SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.LOGIN);
    }

    /**
     * Handle event of "Exit" menu item. Ask a user whether it
     * wants to close the program.
     *
     * @return false if it has been pressed Cancel button.
     */
    public static boolean exit() {
        if (Alerts.showConfirm(RES.getString("exit"), RES.getString("exit.content"))) {
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
        stage.setTitle(RES.getString("change.keyword.title"));
        stage.initModality(Modality.APPLICATION_MODAL);
        SceneSwitcher.set(stage, SceneSwitcher.Scenes.CHANGE_KEYWORD);
    }

    /**
     * Handle event of "Delete account" menu item. Ask the user whether it wants
     * to delete the account. Show Delete modal window
     * if true.
     */
    public static void deleteAccount() {
        boolean isConfirmed = Alerts.showConfirm(RES.getString("delete.account"), RES.getString("delete.account.content"));
        if (isConfirmed) {
            Stage stage = new Stage();
            stage.setTitle(RES.getString("delete.account.title"));
            stage.initModality(Modality.APPLICATION_MODAL);
            SceneSwitcher.set(stage, SceneSwitcher.Scenes.DELETE);
        }
    }

    /**
     * Handle event of "About" menu item.
     */
    public static void about() {

    }
}
