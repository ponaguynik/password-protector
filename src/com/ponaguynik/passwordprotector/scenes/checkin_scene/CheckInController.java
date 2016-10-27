package com.ponaguynik.passwordprotector.scenes.checkin_scene;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CheckInController {

    @FXML
    private TextField nameTF;
    @FXML
    private PasswordField keywordPF, keywordConfPF;
    @FXML
    private TextField hintTF;
    @FXML
    private Button confirmBtn, backBtn;

    private void validate() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText("");
    }
}
