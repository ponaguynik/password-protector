package com.ponaguynik.passwordprotector.scenes.confirm_scene;

import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.other.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ConfirmController {

    public TextField usernameTF;
    public PasswordField keywordPF;
    public Button okBtn;
    public Button cancelBtn;

    @FXML
    private void onOkBtnAction() throws Exception {
        if (DBWorker.checkKeyword(PasswordProtector.currentUser, keywordPF.getText())) {
            DBWorker.deleteUser(PasswordProtector.currentUser);
            Alerts.showInformation("The account was successfully deleted");
            ((Stage) cancelBtn.getScene().getWindow()).close();
            SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.LOGIN);
        } else
            Alerts.showError("Invalid current keyword!");
    }

    @FXML
    private void onCancelBtnAction() {
        if (Alerts.showConfirm("Do you want to cancel?")) {
            ((Stage) cancelBtn.getScene().getWindow()).close();
        }
    }

    public void reset() {
        usernameTF.clear();
        keywordPF.clear();
        keywordPF.requestFocus();
    }
}
