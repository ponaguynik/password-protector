package com.ponaguynik.passwordprotector.scenes.login;



import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.scenes.main.MainController;
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
import java.util.ResourceBundle;

/**
 * The LoginController class is a controller class
 * for login.fxml (Login scene).
 */
public class LoginController {

    /**
     * ResourceBundle object that contains strings of the
     * login.properties file.
     */
    private static ResourceBundle res = ResourceBundle.getBundle("strings.login");

    /**
     * The PasswordProtector image.
     */
    private static final Image PASSWORD_PROTECTOR =
            new Image(LoginController.class.getResourceAsStream("/images/password-protector-30.png"));

    /**
     * The root pane of the Login scene.
     */
    @FXML
    private BorderPane root;
    /**
     * Username text field.
     */
    @FXML
    private TextField usernameTF;
    /**
     * Keyword password field.
     */
    @FXML
    private PasswordField keywordPF;
    /**
     * PasswordProtector label and labels for Username and Keyword fields.
     */
    @FXML
    private Label passProtLab, usernameLab, keywordLab;
    /**
     * Ok, Cancel and Create a new user buttons.
     */
    @FXML
    private Button okBtn, cancelBtn, createUserBtn;

    /**
     * The action event listener method for Ok button.
     * Verify username and keyword. Set Data Forms list from database for Main scene.
     * Set Main scene.
     */
    @FXML
    private void onOkBtnAction() {
        if (isVerified()) {
            PasswordProtector.currentUser = usernameTF.getText();
            reset();
            try {
                MainController.setDataFormsList(DBWorker.getAllDataForms(PasswordProtector.currentUser));
                SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.MAIN);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    /**
     * Initialize the Login scene. Set "default-theme.css" stylesheet
     * for root pane. Set text for all elements of the scene from res.
     * Set PasswordProtector image on PasswordProtector label.
     */
    @FXML
    private void initialize() {
        root.getStylesheets().add(getClass().getResource("/styles/default-theme.css").toExternalForm());
        passProtLab.setGraphic(new ImageView(PASSWORD_PROTECTOR));
        usernameLab.setText(res.getString("username.label"));
        keywordLab.setText(res.getString("keyword.label"));
        okBtn.setText(res.getString("ok.button"));
        cancelBtn.setText(res.getString("cancel.button"));
        createUserBtn.setText(res.getString("create.new.user.button"));
    }

    /**
     * The action event listener method for Cancel button.
     * Use SceneSwitcher.exit() to exit.
     */
    @FXML
    private void onCancelBtnAction() {
        SceneSwitcher.exit();
    }

    /**
     * The action event listener method for 'Create a new user' button.
     * Set Check In scene.
     */
    @FXML
    private void onCreateUserBtnAction() {
        try {
            SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.REGISTER);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Verify Username and Keyword fields.
     * Check if fields aren't empty and Keyword is validated.
     *
     * @return true if the fields are verified.
     */
    private boolean isVerified() {
        if (usernameTF.getText().isEmpty()) {
            Alerts.showWarning(res.getString("username.empty"));
            return false;
        } else if (keywordPF.getText().isEmpty()) {
            Alerts.showWarning(res.getString("keyword.empty"));
            return false;
        }

        if (DBWorker.verifyKeyword(usernameTF.getText(), keywordPF.getText())) {
            Alerts.showInformation(res.getString("welcome"));
            return true;
        } else {
            Alerts.showError(res.getString("invalid"));

            return false;
        }
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
