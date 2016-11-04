package com.ponaguynik.passwordprotector.other;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alerts {

    private static Alert info = new Alert(Alert.AlertType.INFORMATION);
    private static Alert warn = new Alert(Alert.AlertType.WARNING);
    private static Alert error = new Alert(Alert.AlertType.ERROR);
    private static Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);

    public static void showInformation(String message) {
        info.setTitle("Information");
        info.setHeaderText(message);
        info.setContentText("");
        info.showAndWait();
    }

    public static void showInformation(String message, String content) {
        info.setTitle("Information");
        info.setHeaderText(message);
        info.setContentText(content);
        info.showAndWait();
    }

    public static void showWarning(String message) {
        warn.setTitle("Warning");
        warn.setHeaderText(message);
        warn.setContentText("");
        warn.showAndWait();
    }

    public static void showWarning(String message, String content) {
        warn.setTitle("Warning");
        warn.setHeaderText(message);
        warn.setContentText(content);
        warn.showAndWait();
    }

    public static void showError(String message) {
        error.setTitle("Error");
        error.setHeaderText(message);
        error.setContentText("");
        error.showAndWait();
    }

    public static void showError(String message, String content) {
        error.setTitle("Error");
        error.setHeaderText(message);
        error.setContentText(content);
        error.showAndWait();
    }

    public static boolean showConfirm(String message) {
        confirm.setTitle("Confirmation");
        confirm.setHeaderText(message);
        return confirm.showAndWait().get() == ButtonType.OK;
    }

    public static boolean showConfirm(String message, String content) {
        confirm.setTitle("Confirmation");
        confirm.setHeaderText(message);
        confirm.setContentText(content);
        return confirm.showAndWait().get() == ButtonType.OK;
    }
}
