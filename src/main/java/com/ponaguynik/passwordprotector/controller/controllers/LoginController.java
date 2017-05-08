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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The LoginController class is a controller class
 * for login.fxml (Login scene).
 */
public class LoginController {

    private static final ResourceBundle RES = ResourceBundle.getBundle("strings.login");
    private static final Image PASSWORD_PROTECTOR =
            new Image(LoginController.class.getResourceAsStream("/images/password-protector-30.png"));

    @FXML
    private BorderPane root;

    @FXML
    private TextField usernameTF;

    @FXML
    private PasswordField keywordPF;

    @FXML
    private Label passProtLab, usernameLab, keywordLab;

    @FXML
    private Button okBtn, cancelBtn, createUserBtn;

    /**
     * Initialize the Login scene. Set "default-theme.css" stylesheet
     * for root pane. Set text for all elements of the scene from RES.
     * Set PasswordProtector image on passProtLab label.
     */
    @FXML
    private void initialize() {
        root.getStylesheets().add(getClass().getResource("/styles/default-theme.css").toExternalForm());
        passProtLab.setGraphic(new ImageView(PASSWORD_PROTECTOR));
        usernameLab.setText(RES.getString("username.label"));
        keywordLab.setText(RES.getString("keyword.label"));
        okBtn.setText(RES.getString("ok.button"));
        cancelBtn.setText(RES.getString("cancel.button"));
        createUserBtn.setText(RES.getString("create.new.user.button"));
    }

    /**
     * Verify username and keyword. Set Data Forms list from database for Main scene.
     * Set Main scene.
     */
    @FXML
    private void onOkBtnAction() {
        String msg = null;
        try {
            msg = LoginVerifier.verify(usernameTF.getText(), keywordPF.getText());
        } catch (SQLException e) {
            e.printStackTrace();
            Alerts.showError(e.getMessage());
            System.exit(1);
        }

        if (msg == null) {
            PasswordProtector.currentUser = new User(usernameTF.getText());
            reset();
            try {
                MainController.setDataFormsList(DBWorker.getAllDataForms(PasswordProtector.currentUser));
            } catch (SQLException e) {
                e.printStackTrace();
                Alerts.showError(e.getMessage());
                System.exit(1);
            }
            Alerts.showInformation(RES.getString("welcome"));
            SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.MAIN);
        } else
            Alerts.showError(msg);
    }

    /**
     * Use SceneSwitcher.exit() to exit.
     */
    @FXML
    private void onCancelBtnAction() {
        SceneSwitcher.exit();
    }

    /**
     * Set Register scene.
     */
    @FXML
    private void onCreateUserBtnAction() {
        SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.REGISTER);
    }

    /**
     * Reset all fields and request focus on the Username field.
     */
    public void reset() {
        usernameTF.clear();
        keywordPF.clear();
        usernameTF.requestFocus();
    }
}
