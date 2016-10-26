package com.ponaguynik.passwordprotector.scenes.login_scene;

import com.ponaguynik.passwordprotector.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private PasswordField keywordPF;
    @FXML
    private Button okBtn, cancelBtn;
    @FXML
    private TextField nameTF;

    @FXML
    private void onOkBtn() throws IOException {
        SceneSwitcher.set(SceneSwitcher.Scenes.MAIN);
    }
}
