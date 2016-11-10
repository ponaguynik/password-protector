package com.ponaguynik.passwordprotector.scenes.delete_scene;

import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.other.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ResourceBundle;


public class DeleteController {

    private static ResourceBundle res = ResourceBundle.getBundle(PasswordProtector.PATH + "delete");

    @FXML
    private Label usernameLab, keywordLab;
    @FXML
    public TextField usernameTF;
    @FXML
    private PasswordField keywordPF;
    @FXML
    private Button okBtn, cancelBtn;


    @FXML
    private void initialize() {
        usernameLab.setText(res.getString("username.label"));
        keywordLab.setText(res.getString("keyword.label"));
        okBtn.setText(res.getString("ok.button"));
        cancelBtn.setText(res.getString("cancel.button"));
    }

    @FXML
    private void onOkBtnAction() throws Exception {
        if (DBWorker.checkKeyword(PasswordProtector.currentUser, keywordPF.getText())) {
            DBWorker.deleteUser(PasswordProtector.currentUser);
            Alerts.showInformation(res.getString("success"));
            ((Stage) cancelBtn.getScene().getWindow()).close();
            SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.LOGIN);
        } else
            Alerts.showError(res.getString("invalid"));
    }

    @FXML
    private void onCancelBtnAction() {
        if (Alerts.showConfirm(res.getString("cancel"))) {
            ((Stage) cancelBtn.getScene().getWindow()).close();
        }
    }

    public void reset() {
        usernameTF.clear();
        keywordPF.clear();
        keywordPF.requestFocus();
    }
}
