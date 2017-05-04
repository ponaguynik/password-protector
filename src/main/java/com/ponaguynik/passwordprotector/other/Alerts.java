package com.ponaguynik.passwordprotector.other;

/**
 * The abstract Alerts class is used for showing
 * information, warning and error pop up dialogs.
 * It is completely implemented. There are only
 * static fields and methods.
 */

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

public abstract class Alerts {

    private static final Alert INFO = new Alert(Alert.AlertType.INFORMATION);
    private static final Alert WARN = new Alert(Alert.AlertType.WARNING);
    private static final Alert ERROR = new Alert(Alert.AlertType.ERROR);
    private static final Alert CONFIRM = new Alert(Alert.AlertType.CONFIRMATION);

    static {
        INFO.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        WARN.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        ERROR.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        CONFIRM.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    }

    /**
     * Show Information pop up dialog.
     *
     * @param message that is displayed in header area of the dialog.
     */
    public static void showInformation(String message) {
        INFO.setTitle("Information");
        INFO.setHeaderText(message);
        INFO.setContentText("");
        INFO.showAndWait();
    }

    /**
     * Show Information pop up dialog.
     *
     * @param message will be displayed in the header area of the dialog.
     * @param content will be displayed in the content area of the dialog.
     */
    public static void showInformation(String message, String content) {
        INFO.setTitle("Information");
        INFO.setHeaderText(message);
        INFO.setContentText(content);
        INFO.showAndWait();
    }

    /**
     * Show Warning pop up dialog.
     *
     * @param message that is displayed in header area of the dialog.
     */
    public static void showWarning(String message) {
        WARN.setTitle("Warning");
        WARN.setHeaderText(message);
        WARN.setContentText("");
        WARN.showAndWait();
    }

    /**
     * Show Warning pop up dialog.
     *
     * @param message will be displayed in the header area of the dialog.
     * @param content will be displayed in the content area of the dialog.
     */
    public static void showWarning(String message, String content) {
        WARN.setTitle("Warning");
        WARN.setHeaderText(message);
        WARN.setContentText(content);
        WARN.showAndWait();
    }

    /**
     * Show Error pop up dialog.
     *
     * @param message that is displayed in header area of the dialog.
     */
    public static void showError(String message) {
        ERROR.setTitle("Error");
        ERROR.setHeaderText(message);
        ERROR.setContentText("");
        ERROR.showAndWait();
    }

    /**
     * Show Error pop up dialog.
     *
     * @param message will be displayed in the header area of the dialog.
     * @param content will be displayed in the content area of the dialog.
     */
    public static void showError(String message, String content) {
        ERROR.setTitle("Error");
        ERROR.setHeaderText(message);
        ERROR.setContentText(content);
        ERROR.showAndWait();
    }

    /**
     * Show Confirmation pop up dialog.
     *
     * @param message that is displayed in header area of the dialog.
     * @return whether it was pressed OK button.
     */
    public static boolean showConfirm(String message) {
        CONFIRM.setTitle("Confirmation");
        CONFIRM.setHeaderText(message);
        CONFIRM.setContentText("");
        return CONFIRM.showAndWait().get() == ButtonType.OK;
    }

    /**
     * Show Confirmation pop up dialog.
     *
     * @param message will be displayed in the header area of the dialog.
     * @param content will be displayed in the content area of the dialog.
     * @return whether OK button has been pressed .
     */
    public static boolean showConfirm(String message, String content) {
        CONFIRM.setTitle("Confirmation");
        CONFIRM.setHeaderText(message);
        CONFIRM.setContentText(content);
        return CONFIRM.showAndWait().get() == ButtonType.OK;
    }
}
