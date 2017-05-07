package com.ponaguynik.passwordprotector.controller.controllers;

/**
 * The DeleteController class is controller class
 * for delete.view.fxml (Delete scene).
 */

import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;


public class DeleteController {

    /**
     * ResourceBundle object that contains strings of the
     * delete.properties file.
     */
    private static ResourceBundle res = ResourceBundle.getBundle("strings.delete");

    /**
     * The root pane of the Delete scene.
     */
    @FXML
    private BorderPane root;
    /**
     * Username text field.
     */
    @FXML
    public TextField usernameTF;
    /**
     * Keyword password field.
     */
    @FXML
    private PasswordField keywordPF;
    /**
     * Labels for Username and Keyword fields.
     */
    @FXML
    private Label usernameLab, keywordLab;
    /**
     * Ok and Cancel buttons.
     */
    @FXML
    private Button okBtn, cancelBtn;


    /**
     * Initialize the Delte scene. Set "default-theme.css" stylesheet
     * for root pane and set text for all elements of the scene from res.
     */
    @FXML
    private void initialize() {
        root.getStylesheets().add(getClass().getResource("/styles/default-theme.css").toExternalForm());
        usernameLab.setText(res.getString("username.label"));
        keywordLab.setText(res.getString("keyword.label"));
        okBtn.setText(res.getString("ok.button"));
        cancelBtn.setText(res.getString("cancel.button"));
    }

    /**
     * The action event listener method for Ok button.
     * Verify keyword. Delete the user and all its data in the database.
     * Set Login scene.
     */
    @FXML
    private void onOkBtnAction() {
        if (DBWorker.verifyKeyword(PasswordProtector.currentUser, keywordPF.getText())) {
            DBWorker.deleteUser(PasswordProtector.currentUser);
            Alerts.showInformation(res.getString("success"));
            ((Stage) cancelBtn.getScene().getWindow()).close();
            try {
                SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.LOGIN);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        } else
            Alerts.showError(res.getString("invalid"));
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
     * Reset all fields and request focus on the Keyword field.
     */
    public void reset() {
        usernameTF.clear();
        keywordPF.clear();
        keywordPF.requestFocus();
    }
}
