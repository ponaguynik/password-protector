package com.ponaguynik.passwordprotector.other;

import javafx.scene.control.Alert;

public class Alerts {

    private static Alert info = new Alert(Alert.AlertType.INFORMATION);
    private static Alert warn = new Alert(Alert.AlertType.WARNING);
    private static Alert error = new Alert(Alert.AlertType.ERROR);

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
}
