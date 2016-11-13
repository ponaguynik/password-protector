package com.ponaguynik.passwordprotector.scenes.checkin_scene;

import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.other.Password;
import com.ponaguynik.passwordprotector.other.Validator;
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
import java.util.ResourceBundle;

public class CheckInController {

    private static ResourceBundle res = ResourceBundle.getBundle(PasswordProtector.PATH + "checkin");

    private static final Image PASSWORD_PROTECTOR =
            new Image(CheckInController.class.getResourceAsStream("../../res/images/password-protector-30.png"));

    @FXML
    private BorderPane root;
    @FXML
    private Label passProtLab, usernameLab, keywordLab, keywordConfLab;
    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField keywordPF, keywordConfPF;
    @FXML
    private Button confirmBtn, backBtn;


    @FXML
    private void initialize() {
        root.getStylesheets().add(getClass().getResource("../../res/styles/default-theme.css").toExternalForm());
        passProtLab.setGraphic(new ImageView(PASSWORD_PROTECTOR));
        usernameLab.setText(res.getString("username.label"));
        keywordLab.setText(res.getString("keyword.label"));
        keywordConfLab.setText(res.getString("confirm.keyword.label"));
        backBtn.setText(res.getString("back.button"));
        confirmBtn.setText(res.getString("confirm.button"));
    }

    @FXML
    private void onConfirmBtn() {
        if (isValidated(usernameTF.getText(), keywordPF.getText(), keywordConfPF.getText())) {
            if (!DBWorker.userExists(usernameTF.getText())) {
                try {
                    DBWorker.addUser(usernameTF.getText(), Password.getSaltedHash(keywordPF.getText()));
                    Alerts.showInformation(res.getString("account.created"));
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                try {
                    SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.LOGIN);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            } else {
                Alerts.showWarning(String.format(res.getString("already.exists"), usernameTF.getText()));
            }
        }
    }

    @FXML
    private void onBackBtn() {
        try {
            SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.LOGIN);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static boolean isValidated(String username, String keyword, String confirmKey) {
        if (username.isEmpty()) {
            Alerts.showWarning(res.getString("username.empty"));
            return false;
        } else if (keyword.isEmpty()) {
            Alerts.showWarning(res.getString("keyword.empty"));
            return false;
        } else if (confirmKey.isEmpty()) {
            Alerts.showWarning(res.getString("confirm.keyword.empty"));
            return false;
        }

        Validator validator = Validator.getInstance();
        validator.setString(username);
        if (!validate(validator, "Username", 4, 16))
            return false;

        validator.setString(keyword);
        if (!validate(validator, "Keyword", 8, 16))
            return false;

        if (!keyword.equals(confirmKey)) {
            Alerts.showWarning(res.getString("not.match"));
            return false;
        }

        return true;
    }

    public static boolean isValidated(String keyword, String confirmKey) {
        return isValidated("fake", keyword, confirmKey);
    }

    private static boolean validate(Validator validator, String field, int min, int max) {
        if (!validator.lengthGreaterThanMin(min)) {
            Alerts.showWarning(String.format(res.getString("too.short"), field),
                    String.format(res.getString("too.short.content"), field, min));
            return false;
        } else if (!validator.lengthLessThanMax(max)) {
            Alerts.showWarning(String.format(res.getString("too.long"), field),
                    String.format(res.getString("too.long.content"), field, max));
            return false;
        } else if (!validator.startsWithLetter()) {
            Alerts.showWarning(String.format(res.getString("start.letter"), field),
                    String.format(res.getString("start.letter.content"), field));
            return false;
        } else if (!validator.noSpaces()) {
            Alerts.showWarning(res.getString("space"), String.format(res.getString("space.content"), field));
            return false;
        } else if (field.equals("Username") && !validator.onlyLettersAndDigits()) {
            Alerts.showWarning(res.getString("not.permissible"), res.getString("not.permissible.content"));
            return false;
        } else if (field.equals("Keyword") && !validator.hasAllCharacters()) {
            Alerts.showWarning(res.getString("not.enough.characters"), res.getString("not.enough.characters.content"));
            return false;
        } else if (!field.equals("Username") && !validator.onlyPermissibleCharacters()) {
            Alerts.showWarning(res.getString("not.permissible"),
                    String.format(res.getString("consist.of"), field));
            return false;
        }

        return true;
    }

    public void reset() {
        usernameTF.clear();
        keywordPF.clear();
        keywordConfPF.clear();
        usernameTF.requestFocus();
    }
}
