package com.ponaguynik.passwordprotector.scenes.changekey_scene;

import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.other.Alerts;
import com.ponaguynik.passwordprotector.other.Password;
import com.ponaguynik.passwordprotector.scenes.checkin_scene.CheckInController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ChangeKeyController {

    @FXML
    private Button cancelBtn, okBtn;
    @FXML
    private PasswordField currKeyPF, confKeyPF, newKeyPF;

    @FXML
    private void onOkBtnAction() throws Exception {
        if (!DBWorker.checkKeyword(PasswordProtector.currentUser, currKeyPF.getText())) {
            Alerts.showError("Invalid current keyword!");
            return;
        }
        if (CheckInController.isValidated(newKeyPF.getText(), confKeyPF.getText())) {
            DBWorker.updateKeyword(PasswordProtector.currentUser, Password.getSaltedHash(newKeyPF.getText()));
            Alerts.showInformation("Keyword was successfully changed!");
            ((Stage) cancelBtn.getScene().getWindow()).close();
        }
    }

    @FXML
    private void onOkCancelAction() {
        if (Alerts.showConfirm("Do you want to cancel?")) {
            ((Stage) cancelBtn.getScene().getWindow()).close();
        }
    }

    public void reset() {
        currKeyPF.clear();
        confKeyPF.clear();
        newKeyPF.clear();
        currKeyPF.requestFocus();
    }
}
