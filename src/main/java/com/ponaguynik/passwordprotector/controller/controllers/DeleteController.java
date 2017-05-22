package com.ponaguynik.passwordprotector.controller.controllers;


import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.controller.login.LoginVerifier;
import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.model.User;
import com.ponaguynik.passwordprotector.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ResourceBundle;


/**
 * The DeleteController class is controller class
 * for delete.fxml (Delete scene).
 */
public class DeleteController {

    private static final ResourceBundle RES = ResourceBundle.getBundle("strings.delete");

    //GUI elements
    @FXML
    private BorderPane root;

    @FXML
    public TextField usernameTF;

    @FXML
    private PasswordField keywordPF;

    @FXML
    private Label usernameLab, keywordLab;

    @FXML
    private Button okBtn, cancelBtn;


    /**
     * Initialize the Delete scene. Set "default-theme.css" stylesheet
     * for root pane and set text for all elements of the scene from RES.
     */
    @FXML
    private void initialize() {
        root.getStylesheets().add(getClass().getResource("/styles/default-theme.css").toExternalForm());
        usernameLab.setText(RES.getString("username.label"));
        keywordLab.setText(RES.getString("keyword.label"));
        okBtn.setText(RES.getString("ok.button"));
        cancelBtn.setText(RES.getString("cancel.button"));
    }

    /**
     * The action event listener method for Ok button.
     * Verify keyword. Delete the user and all its data in the database.
     * Set Login scene.
     */
    @FXML
    private void onOkBtnAction() {
        User user = new User(PasswordProtector.currentUser.getUsername(), keywordPF.getText());
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
        try {
            DBWorker.deleteUser(PasswordProtector.currentUser);
        } catch (SQLException e) {
            e.printStackTrace();
            Alerts.showError(e.getMessage());
            System.exit(1);
        }
        Alerts.showInformation(RES.getString("success"));
        ((Stage) cancelBtn.getScene().getWindow()).close();
        SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.LOGIN);
    }

    /**
     * The action event listener method for Cancel button.
     * Ask the user whether it wants to cancel. Close the window if true.
     */
    @FXML
    private void onCancelBtnAction() {
        if (Alerts.showConfirm(RES.getString("cancel"))) {
            ((Stage) cancelBtn.getScene().getWindow()).close();
        }
    }

    /**
     * Reset all fields and request focus on the Keyword field.
     */
    public void reset() {
        usernameTF.clear();
        keywordPF.clear();
        keywordPF.requestFocus();
    }
}
