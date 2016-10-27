package com.ponaguynik.passwordprotector.scenes.login_scene;

import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.scenes.main_scene.MainController;
import com.ponaguynik.passwordprotector.scenes.popup.Alerts;
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
    private Button okBtn, cancelBtn;
    @FXML
    private TextField usernameTF;

    @FXML
    private void onOkBtn() throws IOException, SQLException {
        if (isVerified()) {
            PasswordProtector.currentUser = usernameTF.getText();
            MainController.setDataFormsList(DBWorker.getAllDataForms(PasswordProtector.currentUser));
            SceneSwitcher.set(SceneSwitcher.Scenes.MAIN);
        }
    }

    private boolean isVerified() {
        String corrKeyword;

        if (usernameTF.getText().isEmpty()) {
            Alerts.showWarning("Username field is empty!");
            return false;
        }
        else if (keywordPF.getText().isEmpty()) {
            Alerts.showWarning("Keyword field is empty");
            return false;
        }

        try {
            corrKeyword = DBWorker.getKeyword(usernameTF.getText());
        } catch (SQLException e) {
            Alerts.showError("There is no user with such name!");
            return false;
        }

        if (keywordPF.getText().equals(corrKeyword)) {
            Alerts.showInformation("Welcome!");
            return true;
        } else {
            Alerts.showError("Wrong keyword!");
        }

        return false;
    }
}
