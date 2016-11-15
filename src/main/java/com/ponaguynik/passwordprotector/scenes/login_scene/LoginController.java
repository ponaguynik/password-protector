package com.ponaguynik.passwordprotector.scenes.login_scene;

import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.scenes.main_scene.MainController;
import com.ponaguynik.passwordprotector.other.Alerts;
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

public class LoginController {

    private static ResourceBundle res = ResourceBundle.getBundle("strings.login");

    @FXML
    private BorderPane root;
    @FXML
    private Label passProtLab, usernameLab, keywordLab;
    @FXML
    private PasswordField keywordPF;
    @FXML
    private Button okBtn, cancelBtn, createUserBtn;
    @FXML
    private TextField usernameTF;

    private static final Image PASSWORD_PROTECTOR =
            new Image(LoginController.class.getResourceAsStream("/images/password-protector-30.png"));

    @FXML
    private void onOkBtn() {
        if (isVerified()) {
            PasswordProtector.currentUser = usernameTF.getText();
            usernameTF.clear();
            keywordPF.clear();
            try {
                MainController.setDataFormsList(DBWorker.getAllDataForms(PasswordProtector.currentUser));
                SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.MAIN);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

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

    @FXML
    private void onCancelBtnAction() {
        SceneSwitcher.exit();
    }

    @FXML
    private void onCreateUserBtn() {
        try {
            SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.CHECK_IN);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private boolean isVerified() {
        if (usernameTF.getText().isEmpty()) {
            Alerts.showWarning(res.getString("username.empty"));
            return false;
        }
        else if (keywordPF.getText().isEmpty()) {
            Alerts.showWarning(res.getString("keyword.empty"));
            return false;
        }

        try {
            if (DBWorker.checkKeyword(usernameTF.getText(), keywordPF.getText())) {
                Alerts.showInformation(res.getString("welcome"));
                return true;
            } else {
                Alerts.showError(res.getString("invalid"));
            }
        } catch (SQLException e) {
            Alerts.showError(res.getString("invalid"));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return false;
    }

    public void reset() {
        usernameTF.clear();
        keywordPF.clear();
        usernameTF.requestFocus();
    }
}
