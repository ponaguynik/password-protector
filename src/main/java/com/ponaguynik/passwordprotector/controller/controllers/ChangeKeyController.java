package com.ponaguynik.passwordprotector.controller.controllers;

/**
 * The ChangeKeyController class is controller class
 * for changekey.view.fxml (Change Keyword scene).
 */

import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.controller.login.LoginVerifier;
import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.model.User;
import com.ponaguynik.passwordprotector.util.Alerts;
import com.ponaguynik.passwordprotector.util.Password;
import com.ponaguynik.passwordprotector.util.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangeKeyController {

    private static final ResourceBundle RES = ResourceBundle.getBundle("strings.changekey");

    @FXML
    private BorderPane root;

    @FXML
    private PasswordField currKeyPF, confKeyPF, newKeyPF;

    @FXML
    private Label currKeyLab, newKeyLab, confirmKeyLab;

    @FXML
    private Button okBtn, cancelBtn;

    /**
     * Initialize the Change Keyword scene. Set "default-theme.css" stylesheet
     * for root pane and set text for all elements of the scene from RES.
     */
    @FXML
    private void initialize() {
        root.getStylesheets().add(getClass().getResource("/styles/default-theme.css").toExternalForm());
        currKeyLab.setText(RES.getString("current.keyword") + ":");
        newKeyLab.setText(RES.getString("new.keyword") + ":");
        confirmKeyLab.setText(RES.getString("confirm.keyword") + ":");
        okBtn.setText(RES.getString("ok.button"));
        cancelBtn.setText(RES.getString("cancel.button"));
    }

    /**
     * The action event listener method for Ok button.
     * Verifies Current Keyword and validates all fields.
     * Changes keyword in a database.
     */
    @FXML
    private void onOkBtnAction() {
        User user = new User(PasswordProtector.currentUser.getUsername(), currKeyPF.getText());
        String msg = null;
        try {
            msg = LoginVerifier.verify(user);
        } catch (SQLException e) {
            e.printStackTrace();
            Alerts.showError(e.getMessage());
            System.exit(1);
        }
        if (msg != null) {
            Alerts.showError(msg);
            return;
        }

        String[] msgs = validate(newKeyPF.getText());
        if (!newKeyPF.getText().equals(confKeyPF.getText()))
            msgs = new String[]{ String.format(RES.getString("not.match"),
                    RES.getString("confirm.keyword"),
                    RES.getString("new.keyword")), "" };
        if (msgs == null) {
            user.setKeyword(Password.getSaltedHash(newKeyPF.getText()));
            try {
                DBWorker.updateKeyword(user);
            } catch (SQLException e) {
                e.printStackTrace();
                Alerts.showError(e.getMessage());
                System.exit(1);
            }
            Alerts.showInformation(RES.getString("success"));
            ((Stage) cancelBtn.getScene().getWindow()).close();
        } else
            Alerts.showWarning(msgs[0], msgs[1]);
    }

    /**
     * The action event listener method for Cancel button.
     * Asks the user whether it wants to cancel. Closes the window if true.
     */
    @FXML
    private void onCancelBtnAction() {
        if (Alerts.showConfirm(RES.getString("cancel"))) {
            ((Stage) cancelBtn.getScene().getWindow()).close();
        }
    }

    private String[] validate(String keyword) {
        String[] msg;
        if ((msg = Validator.validateAsKeyword(RES.getString("new.keyword"), keyword)) != null)
            return msg;
        else
            return null;
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
