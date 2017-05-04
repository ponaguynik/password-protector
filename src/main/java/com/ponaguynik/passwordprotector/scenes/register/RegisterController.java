package com.ponaguynik.passwordprotector.scenes.register;


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

/**
 * The RegisterController class is controller class
 * for register.fxml (Register scene).
 */
public class RegisterController {

    /**
     * ResourceBundle object that contains strings of the
     * register.properties file.
     */
    private static ResourceBundle res = ResourceBundle.getBundle("strings.register");

    /**
     * The PasswordProtector image.
     */
    private static final Image PASSWORD_PROTECTOR =
            new Image(RegisterController.class.getResourceAsStream("/images/password-protector-30.png"));

    /**
     * The root pane of the Register scene.
     */
    @FXML
    private BorderPane root;
    /**
     * Username text field.
     */
    @FXML
    private TextField usernameTF;
    /**
     * Keyword and Confirm Keyword password fields.
     */
    @FXML
    private PasswordField keywordPF, keywordConfPF;
    /**
     * PasswordProtector label and labels for Username, Keyword and
     * Confirm Keyword fields.
     */
    @FXML
    private Label passProtLab, usernameLab, keywordLab, keywordConfLab;
    /**
     * Confirm and Back buttons.
     */
    @FXML
    private Button confirmBtn, backBtn;


    /**
     * Initialize the Register scene. Set "default-theme.css" stylesheet
     * for root pane and set text for all elements of the scene from res.
     * Set PasswordProtector image on PasswordProtector label.
     */
    @FXML
    private void initialize() {
        root.getStylesheets().add(getClass().getResource("/styles/default-theme.css").toExternalForm());
        passProtLab.setGraphic(new ImageView(PASSWORD_PROTECTOR));
        usernameLab.setText(res.getString("username.label"));
        keywordLab.setText(res.getString("keyword.label"));
        keywordConfLab.setText(res.getString("confirm.keyword.label"));
        backBtn.setText(res.getString("back.button"));
        confirmBtn.setText(res.getString("confirm.button"));
    }

    /**
     * The action event listener method for Confirm button.
     * Validates all fields. Checks whether user with such username exists.
     * Adds a user to the database.
     */
    @FXML
    private void onConfirmBtnAction() {
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

    /**
     * The action event listener method for Back button.
     * Set Login scene.
     */
    @FXML
    private void onBackBtn() {
        try {
            SceneSwitcher.set(PasswordProtector.primaryStage, SceneSwitcher.Scenes.LOGIN);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Validate username, keyword and confirm keyword.
     *
     * @return true if it is validated.
     */
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

        Validator validator = new Validator(username);
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

    /**
     * Validate keyword and confirm keyword.
     *
     * @return true if it is validated.
     */
    public static boolean isValidated(String keyword, String confirmKey) {
        return isValidated("fake", keyword, confirmKey);
    }

    /**
     * Validate a string.
     *
     * @param validator is Validator object.
     * @param str is the string that should be validated.
     * @param min length of the str.
     * @param max length of the str.
     * @return true if str is validated.
     */
    private static boolean validate(Validator validator, String str, int min, int max) {
        if (!validator.lengthGreaterThanMin(min)) {
            Alerts.showWarning(String.format(res.getString("too.short"), str),
                    String.format(res.getString("too.short.content"), str, min));
            return false;
        } else if (!validator.lengthLessThanMax(max)) {
            Alerts.showWarning(String.format(res.getString("too.long"), str),
                    String.format(res.getString("too.long.content"), str, max));
            return false;
        } else if (!validator.startsWithLetter()) {
            Alerts.showWarning(String.format(res.getString("start.letter"), str),
                    String.format(res.getString("start.letter.content"), str));
            return false;
        } else if (!validator.noSpaces()) {
            Alerts.showWarning(res.getString("space"), String.format(res.getString("space.content"), str));
            return false;
        } else if (str.equals("Username") && !validator.onlyLettersAndDigits()) {
            Alerts.showWarning(res.getString("not.permissible"), res.getString("not.permissible.content"));
            return false;
        } else if (str.equals("Keyword") && !validator.hasAllCharacters()) {
            Alerts.showWarning(res.getString("not.enough.characters"), res.getString("not.enough.characters.content"));
            return false;
        } else if (!str.equals("Username") && !validator.onlyPermissibleCharacters()) {
            Alerts.showWarning(res.getString("not.permissible"),
                    String.format(res.getString("consist.of"), str));
            return false;
        }

        return true;
    }

    /**
     * Reset all fields and request focus on Username field.
     */
    public void reset() {
        usernameTF.clear();
        keywordPF.clear();
        keywordConfPF.clear();
        usernameTF.requestFocus();
    }
}
