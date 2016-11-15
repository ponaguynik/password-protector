package com.ponaguynik.passwordprotector.other;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alerts {

    private static final Alert INFO = new Alert(Alert.AlertType.INFORMATION);
    private static final Alert WARN = new Alert(Alert.AlertType.WARNING);
    private static final Alert ERROR = new Alert(Alert.AlertType.ERROR);
    private static final Alert CONFIRM = new Alert(Alert.AlertType.CONFIRMATION);

    public static void showInformation(String message) {
        INFO.setTitle("Information");
        INFO.setHeaderText(message);
        INFO.setContentText("");
        INFO.showAndWait();
    }

    public static void showInformation(String message, String content) {
        INFO.setTitle("Information");
        INFO.setHeaderText(message);
        INFO.setContentText(content);
        INFO.showAndWait();
    }

    public static void showWarning(String message) {
        WARN.setTitle("Warning");
        WARN.setHeaderText(message);
        WARN.setContentText("");
        WARN.showAndWait();
    }

    public static void showWarning(String message, String content) {
        WARN.setTitle("Warning");
        WARN.setHeaderText(message);
        WARN.setContentText(content);
        WARN.showAndWait();
    }

    public static void showError(String message) {
        ERROR.setTitle("Error");
        ERROR.setHeaderText(message);
        ERROR.setContentText("");
        ERROR.showAndWait();
    }

    public static void showError(String message, String content) {
        ERROR.setTitle("Error");
        ERROR.setHeaderText(message);
        ERROR.setContentText(content);
        ERROR.showAndWait();
    }

    public static boolean showConfirm(String message) {
        CONFIRM.setTitle("Confirmation");
        CONFIRM.setHeaderText(message);
        CONFIRM.setContentText("");
        return CONFIRM.showAndWait().get() == ButtonType.OK;
    }

    public static boolean showConfirm(String message, String content) {
        CONFIRM.setTitle("Confirmation");
        CONFIRM.setHeaderText(message);
        CONFIRM.setContentText(content);
        return CONFIRM.showAndWait().get() == ButtonType.OK;
    }
}
