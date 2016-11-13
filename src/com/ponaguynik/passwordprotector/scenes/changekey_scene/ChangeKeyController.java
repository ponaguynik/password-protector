package com.ponaguynik.passwordprotector.scenes.changekey_scene;

import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.other.Alerts;
import com.ponaguynik.passwordprotector.other.Password;
import com.ponaguynik.passwordprotector.scenes.checkin_scene.CheckInController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class ChangeKeyController {

    private static final ResourceBundle res = ResourceBundle.getBundle(PasswordProtector.PATH + "changekey");

    @FXML
    private BorderPane root;
    @FXML
    private Label currKeyLab, newKeyLab, confirmKeyLab;
    @FXML
    private Button cancelBtn, okBtn;
    @FXML
    private PasswordField currKeyPF, confKeyPF, newKeyPF;

    @FXML
    private void initialize() {
        root.getStylesheets().add(getClass().getResource("../../res/styles/default-theme.css").toExternalForm());
        currKeyLab.setText(res.getString("current.keyword.label"));
        newKeyLab.setText(res.getString("new.keyword.label"));
        confirmKeyLab.setText(res.getString("confirm.keyword.label"));
    }

    @FXML
    private void onOkBtnAction() throws Exception {
        if (!DBWorker.checkKeyword(PasswordProtector.currentUser, currKeyPF.getText())) {
            Alerts.showError(res.getString("invalid"));
            return;
        }
        if (CheckInController.isValidated(newKeyPF.getText(), confKeyPF.getText())) {
            DBWorker.updateKeyword(PasswordProtector.currentUser, Password.getSaltedHash(newKeyPF.getText()));
            Alerts.showInformation(res.getString("success"));
            ((Stage) cancelBtn.getScene().getWindow()).close();
        }
    }

    @FXML
    private void onOkCancelAction() {
        if (Alerts.showConfirm(res.getString("cancel"))) {
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
