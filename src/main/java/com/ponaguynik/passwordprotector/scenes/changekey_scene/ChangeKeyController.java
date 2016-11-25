package com.ponaguynik.passwordprotector.scenes.changekey_scene;

/**
 * The ChangeKeyController class is controller class
 * for changekey.fxml (Change Keyword scene).
 */

import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.other.Alerts;
import com.ponaguynik.passwordprotector.other.Password;
import com.ponaguynik.passwordprotector.scenes.checkin_scene.CheckInController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class ChangeKeyController {

    /**
     * ResourceBundle object that contains strings of the
     * changekey.properties file.
     */
    private static final ResourceBundle res = ResourceBundle.getBundle("strings.changekey");

    /**
     * The root pane of the Change Keyword scene.
     */
    @FXML
    private BorderPane root;
    /**
     * Current Keyword, New Keyword and Confirm Keyword password fields.
     */
    @FXML
    private PasswordField currKeyPF, confKeyPF, newKeyPF;
    /**
     * Labels for Current Keyword, New Keyword and Confirm Keyword password fields.
     */
    @FXML
    private Label currKeyLab, newKeyLab, confirmKeyLab;
    /**
     * Ok and Cancel buttons.
     */
    @FXML
    private Button okBtn, cancelBtn;

    /**
     * Initialize the Change Keyword scene. Set "default-theme.css" stylesheet
     * for root pane and set text for all elements of the scene from res.
     */
    @FXML
    private void initialize() {
        root.getStylesheets().add(getClass().getResource("/styles/default-theme.css").toExternalForm());
        currKeyLab.setText(res.getString("current.keyword.label"));
        newKeyLab.setText(res.getString("new.keyword.label"));
        confirmKeyLab.setText(res.getString("confirm.keyword.label"));
        okBtn.setText(res.getString("ok.button"));
        cancelBtn.setText(res.getString("cancel.button"));
    }

    /**
     * The action event listener method for Ok button.
     * Verifies Current Keyword and validates all fields.
     * Changes keyword in a database.
     */
    @FXML
    private void onOkBtnAction() {
        if (!DBWorker.verifyKeyword(PasswordProtector.currentUser, currKeyPF.getText())) {
            Alerts.showError(res.getString("invalid"));
            return;
        }
        if (CheckInController.isValidated(newKeyPF.getText(), confKeyPF.getText())) {
            DBWorker.updateKeyword(PasswordProtector.currentUser, Password.getSaltedHash(newKeyPF.getText()));
            Alerts.showInformation(res.getString("success"));
            ((Stage) cancelBtn.getScene().getWindow()).close();
        }
    }

    /**
     * The action event listener method for Cancel button.
     * Asks the user whether it wants to cancel. Closes the window if true.
     */
    @FXML
    private void onCancelBtnAction() {
        if (Alerts.showConfirm(res.getString("cancel"))) {
            ((Stage) cancelBtn.getScene().getWindow()).close();
        }
    }

    /**
     * Reset all fields and request focus on Current Keyword field.
     */
    public void reset() {
        currKeyPF.clear();
        confKeyPF.clear();
        newKeyPF.clear();
        currKeyPF.requestFocus();
    }
}
