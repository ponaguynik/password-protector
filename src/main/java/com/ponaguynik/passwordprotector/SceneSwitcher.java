package com.ponaguynik.passwordprotector;

import com.ponaguynik.passwordprotector.database.DBConnector;
import com.ponaguynik.passwordprotector.other.Alerts;
import com.ponaguynik.passwordprotector.scenes.changekey_scene.ChangeKeyController;
import com.ponaguynik.passwordprotector.scenes.checkin_scene.CheckInController;
import com.ponaguynik.passwordprotector.scenes.delete_scene.DeleteController;
import com.ponaguynik.passwordprotector.scenes.login_scene.LoginController;
import com.ponaguynik.passwordprotector.scenes.main_scene.MainController;
import com.ponaguynik.passwordprotector.other.MenuHelper;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class SceneSwitcher {

    /**
     * ResourceBundle object that contains strings of the
     * login.properties file.
     */
    private static ResourceBundle res = ResourceBundle.getBundle("strings.login");

    public enum Scenes {
        LOGIN, CHECK_IN, MAIN, CHANGE_KEYWORD, DELETE
    }

    /**
     * Login, Check In, Main, Change Keyword and Delete FXML loaders.
     */
    private static FXMLLoader loginLoader = new FXMLLoader(PasswordProtector.class.getResource("/fxml/login.fxml"));
    private static FXMLLoader checkInLoader = new FXMLLoader(PasswordProtector.class.getResource("/fxml/check-in.fxml"));
    private static FXMLLoader mainLoader = new FXMLLoader(PasswordProtector.class.getResource("/fxml/main.fxml"));
    private static FXMLLoader changeKeyLoader = new FXMLLoader(PasswordProtector.class.getResource("/fxml/changekey.fxml"));
    private static FXMLLoader deleteLoader = new FXMLLoader(PasswordProtector.class.getResource("/fxml/delete.fxml"));

    /**
     * Login, Check In, Main, Change Keyword and Delete scenes.
     */
    private static Scene loginScene;
    private static Scene checkInScene;
    private static Scene mainScene;
    private static Scene changeKeyScene;
    private static Scene deleteScene;

    /**
     * Load scenes from loaders.
     */
    static {
        try {
            loginScene = new Scene(loginLoader.load());
            checkInScene = new Scene(checkInLoader.load());
            mainScene = new Scene(mainLoader.load());
            changeKeyScene = new Scene(changeKeyLoader.load());
            deleteScene = new Scene(deleteLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    /**
     * Set a scene to a stage.
     */
    public static void set(Stage stage, Scenes scene) throws IOException {
        stage.hide();
        switch (scene) {
            case LOGIN:
                setLoginScene(stage);
                break;
            case CHECK_IN:
                setCheckInScene(stage);
                break;
            case MAIN:
                setMainScene(stage);
                break;
            case CHANGE_KEYWORD:
                setChangeKeyScene(stage);
                break;
            case DELETE:
                setDeleteScene(stage);
                break;
            default:
                throw new IOException("No such scene");
        }
    }

    private static void setLoginScene(Stage stage) {
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            if (!exit())
            event.consume();
        });
        LoginController contr = (LoginController) getController(Scenes.LOGIN);
        assert contr != null;
        contr.reset();
        stage.setScene(loginScene);
        stage.show();
    }

    private static void setCheckInScene(Stage stage) {
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            if (!exit())
                event.consume();
        });
        CheckInController contr = (CheckInController) getController(Scenes.CHECK_IN);
        assert contr != null;
        contr.reset();
        stage.setScene(checkInScene);
        stage.show();
    }

    private static void setMainScene(Stage stage) {
        stage.setResizable(true);
        stage.setOnCloseRequest(event -> {
            if (!MenuHelper.exit())
                event.consume();

        });
        MainController contr = (MainController) getController(Scenes.MAIN);
        assert contr != null;
        contr.reset();
        stage.setScene(mainScene);
        stage.show();
    }

    private static void setChangeKeyScene(Stage stage) {
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            if (!Alerts.showConfirm(res.getString("cancel")))
                event.consume();
        });
        ChangeKeyController contr = (ChangeKeyController) getController(Scenes.CHANGE_KEYWORD);
        assert contr != null;
        contr.reset();
        stage.setScene(changeKeyScene);
        stage.showAndWait();
    }

    private static void setDeleteScene(Stage stage) {
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            if (!Alerts.showConfirm(res.getString("cancel")))
                event.consume();
        });
        DeleteController contr = (DeleteController) getController(Scenes.DELETE);
        assert contr != null;
        contr.reset();
        contr.usernameTF.setText(PasswordProtector.currentUser);
        stage.setScene(deleteScene);
        stage.showAndWait();
    }

    public static boolean exit() {
        if (Alerts.showConfirm(res.getString("exit"))) {
            DBConnector.close();
            Platform.exit();
            return true;
        } else return false;
    }

    public static Object getController(Scenes scene) {
        switch (scene) {
            case LOGIN:
                return loginLoader.getController();
            case CHECK_IN:
                return checkInLoader.getController();
            case MAIN:
                return mainLoader.getController();
            case CHANGE_KEYWORD:
                return changeKeyLoader.getController();
            case DELETE:
                return deleteLoader.getController();
            default:
                return null;
        }
    }
}
