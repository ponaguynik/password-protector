package com.ponaguynik.passwordprotector.scenes.login_scene;

import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.other.Password;
import com.ponaguynik.passwordprotector.scenes.main_scene.MainController;
import com.ponaguynik.passwordprotector.other.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private PasswordField keywordPF;
    @FXML
    private Button okBtn, cancelBtn, createUserBtn;
    @FXML
    private TextField usernameTF;

    @FXML
    private void onOkBtn() {
        //Temporary test code
        if (true/*isVerified()*/) {
            PasswordProtector.currentUser = "test"/*usernameTF.getText()*/;
            try {
                MainController.setDataFormsList(DBWorker.getAllDataForms(PasswordProtector.currentUser));
                SceneSwitcher.set(SceneSwitcher.Scenes.MAIN);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    @FXML
    private void onCancelBtn() {

    }

    @FXML
    private void onCreateUserBtn() {
        try {
            SceneSwitcher.set(SceneSwitcher.Scenes.CHECK_IN);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private boolean isVerified() {

        if (usernameTF.getText().isEmpty()) {
            Alerts.showWarning("Username field is empty!");
            return false;
        }
        else if (keywordPF.getText().isEmpty()) {
            Alerts.showWarning("Keyword field is empty");
            return false;
        }

        try {
            if (DBWorker.checkKeyword(usernameTF.getText(), keywordPF.getText())) {
                Alerts.showInformation("Welcome!");
                return true;
            } else {
                Alerts.showError("Invalid username or password!");
            }
        } catch (SQLException e) {
            Alerts.showError("Invalid username or password!");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return false;
    }
}
