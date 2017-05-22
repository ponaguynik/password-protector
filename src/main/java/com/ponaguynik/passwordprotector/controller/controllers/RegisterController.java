package com.ponaguynik.passwordprotector.controller.controllers;


import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.controller.register.Registrar;
import com.ponaguynik.passwordprotector.exceptions.UserAlreadyExists;
import com.ponaguynik.passwordprotector.model.User;
import com.ponaguynik.passwordprotector.util.Alerts;
import com.ponaguynik.passwordprotector.util.Password;
import com.ponaguynik.passwordprotector.util.Validator;
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
 * The RegisterController class is controller class
 * for register.fxml (Register scene).
 */
public class RegisterController {

    private static final ResourceBundle RES = ResourceBundle.getBundle("strings.register");
    private static final Image PASSWORD_PROTECTOR =
            new Image(RegisterController.class.getResourceAsStream("/images/password-protector-30.png"));

    //GUI elements
    @FXML
    private BorderPane root;

    @FXML
    private TextField usernameTF;

    @FXML
    private PasswordField keywordPF, confirmKeyPF;

    @FXML
    private Label passProtLab, usernameLab, keywordLab, confirmKeyLab;

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
        usernameLab.setText(RES.getString("username") + ":");
        keywordLab.setText(RES.getString("keyword") + ":");
        confirmKeyLab.setText(RES.getString("confirm.keyword") + ":");
        backBtn.setText(RES.getString("back.button"));
        confirmBtn.setText(RES.getString("confirm.button"));
    }

    /**
     * The action event listener method for Confirm button.
     * Validate all input fields. Register user. Switch to the Login scene.
     */
    @FXML
    private void onConfirmBtnAction() {
        String username = usernameTF.getText();
        String keyword = keywordPF.getText();
        String confirmKey = confirmKeyPF.getText();
        String[] msg = validate(username, keyword, confirmKey);

        if (msg != null) {
            Alerts.showWarning(msg[0], msg[1]);
            return;
        }
        try {
            Registrar.register(new User(username, Password.getSaltedHash(keyword)));
            Alerts.showInformation(RES.getString("account.created"));
            SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.LOGIN);
        } catch (UserAlreadyExists e) {
            Alerts.showWarning(String.format(RES.getString("already.exists"), username));
        } catch (SQLException e) {
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
        SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.LOGIN);
    }

    private String[] validate(String username, String keyword, String confirmKeyword) {
        String[] msg;
        if ((msg = Validator.validateAsUsername(RES.getString("username"), username)) != null)
            return msg;
        else if ((msg = Validator.validateAsKeyword(RES.getString("keyword"), keyword)) != null)
            return msg;
        else if (!keyword.equals(confirmKeyword)) {
            msg = new String[] {String.format(RES.getString("not.match"),
                    RES.getString("keyword"), RES.getString("confirm.keyword")), ""};
            return msg;
        }
        else
            return null;
    }

    /**
     * Reset all fields and request focus on the Username field.
     */
    public void reset() {
        usernameTF.clear();
        keywordPF.clear();
        confirmKeyPF.clear();
        usernameTF.requestFocus();
    }
}
