package com.ponaguynik.passwordprotector.controller.controllers;


import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.controller.register.Registrar;
import com.ponaguynik.passwordprotector.controller.register.RegistrationValidator;
import com.ponaguynik.passwordprotector.exceptions.UserAlreadyExists;
import com.ponaguynik.passwordprotector.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**
 * The RegisterController class is controller class
 * for register.fxml (Register scene).
 */
public class RegisterController {

    private static ResourceBundle RES = ResourceBundle.getBundle("strings.register");
    private static final Image PASSWORD_PROTECTOR =
            new Image(RegisterController.class.getResourceAsStream("/images/password-protector-30.png"));

    //GUI elements
    @FXML
    private BorderPane root;

    @FXML
    private TextField usernameTF;

    @FXML
    private PasswordField keywordPF, keywordConfPF;

    @FXML
    private Label passProtLab, usernameLab, keywordLab, keywordConfLab;

    @FXML
    private Button confirmBtn, backBtn;


    /**
     * Initialize the Register scene. Set "default-theme.css" stylesheet
     * for root pane and set text for all elements of the scene from RES.
     * Set PasswordProtector image on PasswordProtector label.
     */
    @FXML
    private void initialize() {
        root.getStylesheets().add(getClass().getResource("/styles/default-theme.css").toExternalForm());
        passProtLab.setGraphic(new ImageView(PASSWORD_PROTECTOR));
        usernameLab.setText(RES.getString("username.label"));
        keywordLab.setText(RES.getString("keyword.label"));
        keywordConfLab.setText(RES.getString("confirm.keyword.label"));
        backBtn.setText(RES.getString("back.button"));
        confirmBtn.setText(RES.getString("confirm.button"));
    }

    /**
     * The action event listener method for Confirm button.
     * Validates all input fields. Register user.
     * Switches to the Login scene.
     */
    @FXML
    private void onConfirmBtnAction() {
        String[] msg = RegistrationValidator.validate(usernameTF.getText(), keywordPF.getText(), keywordConfPF.getText());
        if (msg != null) {
            if (msg[1] != null)
                Alerts.showWarning(msg[0], msg[1]);
            else
                Alerts.showWarning(msg[0]);
            return;
        }

        try {
            Registrar.register(usernameTF.getText(), keywordPF.getText());
            Alerts.showInformation(RES.getString("account.created"));
        } catch (UserAlreadyExists e) {
            Alerts.showWarning(String.format(RES.getString("already.exists"), usernameTF.getText()));
        } catch (SQLException e) {
            e.printStackTrace();
            Alerts.showError(e.getMessage());
            System.exit(1);
        }

        try {
            SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.LOGIN);
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showError(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * The action event listener method for Back button.
     * Set Login scene.
     */
    @FXML
    private void onBackBtn() {
        try {
            SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.LOGIN);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Reset all fields and request focus on the Username field.
     */
    public void reset() {
        usernameTF.clear();
        keywordPF.clear();
        keywordConfPF.clear();
        usernameTF.requestFocus();
    }
}
