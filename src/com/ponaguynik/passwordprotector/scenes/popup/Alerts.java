package com.ponaguynik.passwordprotector.scenes.popup;

import javafx.scene.control.Alert;

public class Alerts {

    private static Alert info = new Alert(Alert.AlertType.INFORMATION);
    private static Alert warn = new Alert(Alert.AlertType.WARNING);
    private static Alert error = new Alert(Alert.AlertType.ERROR);

    public static void showInformation(String message) {
        info.setTitle("Information");
        info.setHeaderText(message);
        info.showAndWait();
    }

    public static void showWarning(String message) {
        warn.setTitle("Warning");
        warn.setHeaderText(message);
        warn.showAndWait();
    }

    public static void showError(String message) {
        error.setTitle("Error");
        error.setHeaderText(message);
        error.showAndWait();
    }
}
