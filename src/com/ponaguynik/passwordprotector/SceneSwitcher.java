package com.ponaguynik.passwordprotector;

import com.ponaguynik.passwordprotector.database.DBConnector;
import com.ponaguynik.passwordprotector.other.Alerts;
import com.ponaguynik.passwordprotector.scenes.checkin_scene.CheckInController;
import com.ponaguynik.passwordprotector.scenes.login_scene.LoginController;
import com.ponaguynik.passwordprotector.scenes.main_scene.MainController;
import com.ponaguynik.passwordprotector.scenes.main_scene.MenuHelper;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    public enum Scenes {
        LOGIN, CHECK_IN, MAIN
    }

    private static Scene current;

    private static Stage primaryStage = PasswordProtector.primaryStage;

    private static FXMLLoader loginLoader = new FXMLLoader(PasswordProtector.class.getResource("scenes/login_scene/login.fxml"));
    private static FXMLLoader checkInLoader = new FXMLLoader(PasswordProtector.class.getResource("scenes/checkin_scene/check-in.fxml"));
    private static FXMLLoader mainLoader = new FXMLLoader(PasswordProtector.class.getResource("scenes/main_scene/main.fxml"));

    private static Scene loginScene;
    private static Scene checkInScene;
    private static Scene mainScene;

    static {
        try {
            loginScene = new Scene(loginLoader.load());
            checkInScene = new Scene(checkInLoader.load());
            mainScene = new Scene(mainLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void set(Scenes scene) throws IOException {
        primaryStage.hide();
        switch (scene) {
            case LOGIN:
                setLoginScene();
                break;
            case CHECK_IN:
                setCheckInScene();
                break;
            case MAIN:
                setMainScene();
                break;
            default:
                throw new IOException("No such scene.");
    }
        primaryStage.setScene(current);
        primaryStage.show();
    }

    private static void setLoginScene() {
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            if (!exit())
            event.consume();
        });
        LoginController contr = (LoginController) getController(Scenes.LOGIN);
        assert contr != null;
        contr.reset();
        current = loginScene;
    }

    private static void setCheckInScene() {
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            if (!exit())
                event.consume();
        });
        CheckInController contr = (CheckInController) getController(Scenes.CHECK_IN);
        assert contr != null;
        contr.reset();
        current = checkInScene;
    }

    private static void setMainScene() {
        primaryStage.setResizable(true);
        primaryStage.setOnCloseRequest(event -> {
            if (!MenuHelper.exit())
                event.consume();

        });
        MainController contr = (MainController) getController(Scenes.MAIN);
        assert contr != null;
        contr.reset();
        current = mainScene;
    }

    public static boolean exit() {
        if (Alerts.showConfirm("Are you sure you want to exit?")) {
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
            default:
                return null;
        }
    }
}
