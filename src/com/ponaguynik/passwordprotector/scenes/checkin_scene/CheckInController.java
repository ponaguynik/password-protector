package com.ponaguynik.passwordprotector.scenes.checkin_scene;

import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.other.Validator;
import com.ponaguynik.passwordprotector.other.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CheckInController {

    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField keywordPF, keywordConfPF;
    @FXML
    private TextField hintTF;
    @FXML
    private Button confirmBtn, backBtn;

    @FXML
    private void onConfirmBtn() {
        if (isValidated()) {
            if (!DBWorker.userExists(usernameTF.getText())) {
                DBWorker.addUser(usernameTF.getText(), keywordPF.getText());
                Alerts.showInformation("New account has been successfully created!");
                try {
                    SceneSwitcher.set(SceneSwitcher.Scenes.LOGIN);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            } else {
                Alerts.showWarning(String.format("%s already exists!", usernameTF.getText()));
            }
        }
    }

    @FXML
    private void onBackBtn() {
        try {
            SceneSwitcher.set(SceneSwitcher.Scenes.LOGIN);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    private boolean isValidated() {
        if (usernameTF.getText().isEmpty()) {
            Alerts.showWarning("Username field is empty!");
            return false;
        } else if (keywordPF.getText().isEmpty()) {
            Alerts.showWarning("Keyword field is empty!");
            return false;
        } else if (keywordConfPF.getText().isEmpty()) {
            Alerts.showWarning("Confirm Keyword field is empty!");
            return false;
        }

        Validator validator = Validator.getInstance();
        validator.setString(usernameTF.getText());
        if (!validate(validator, "Username", 4, 16))
            return false;

        validator.setString(keywordPF.getText());
        if (!validate(validator, "Keyword", 8, 16))
            return false;

        if (!keywordPF.getText().equals(keywordConfPF.getText())) {
            Alerts.showWarning("The Keyword does not match Confirm Keyword");
            return false;
        }

        validator.setString(hintTF.getText());
        if (!validate(validator, "Hint", 0, 32))
            return false;

        return true;
    }

    private boolean validate(Validator validator, String field, int min, int max) {
        if (!validator.lengthGreaterThanMin(min)) {
            Alerts.showWarning(String.format("The %s is too short!", field),
                    String.format("The %s must be at least %d characters.", field, min));
            return false;
        } else if (!validator.lengthLessThanMax(max)) {
            Alerts.showWarning(String.format("The %s is too long!", field),
                    String.format("The %s must be less than %d characters.", field, max));
            return false;
        } else if (!validator.startsWithLetter() && !field.equals("Hint")) {
            Alerts.showWarning(String.format("The %s does not start with a letter!", field),
                    String.format("The %s must start with an English letter.", field));
            return false;
        } else if (!validator.noSpaces() && !field.equals("Hint")) {
            Alerts.showWarning("There is a space!", String.format("The %s must not include spaces.", field));
            return false;
        } else if (field.equals("Username") && !validator.onlyLettersAndDigits()) {
            Alerts.showWarning("Not permissible characters used!", "The Username must consist of only English letters" +
                    " and digits.");
            return false;
        } else if (field.equals("Keyword") && !validator.hasAllCharacters()) {
            Alerts.showWarning("Not enough characters used!", "The Keyword must include at least one number (0-9), " +
                    "lowercase letter (a-z), uppercase letter (A-Z) and one special character (@_#()^!.,~%&:;/)");
            return false;
        } else if (!field.equals("Username") && !validator.onlyPermissibleCharacters() && !field.equals("Hint")) {
            Alerts.showWarning("Not permissible characters used!",
                    String.format("The %s must consist of only English letters (a-z, A-Z), digits (0-9) " +
                            "and special characters (@_#()^!.,~%%&:;/)).", field));
            return false;
        }

        return true;
    }
}
