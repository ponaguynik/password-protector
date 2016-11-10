package com.ponaguynik.passwordprotector.scenes.main_scene;

import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.other.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ResourceBundle;

public class DataForm extends GridPane {

    private static ResourceBundle res = ResourceBundle.getBundle(PasswordProtector.PATH + "dataform");

    @FXML
    private Label loginLab, passwordLab;
    @FXML
    private CheckBox showCB;
    @FXML
    private Button editBtn, deleteBtn;
    @FXML
    private TextField titleTF, loginTF, passwordTF;
    @FXML
    private PasswordField passwordField;

    private static boolean initialized = false;

    private boolean editMode;

    private final int id;

    //Images
    private static final Image X  = new Image(DataForm.class.getResourceAsStream("../../res/images/x-grey.png"));
    private static final Image X_LIGHT  = new Image(DataForm.class.getResourceAsStream("../../res/images/x-lightgrey.png"));


    public DataForm(int id) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ponaguynik/passwordprotector/scenes/main_scene/dataform.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.id = id;
        editMode = false;
        passwordField.textProperty().bindBidirectional(passwordTF.textProperty());
        passwordField.setVisible(true);
        passwordTF.setVisible(false);
        onEditBtnAction();
    }

    public DataForm(int id, String title, String login, String password) {
        this(id);
        titleTF.setText(title);
        loginTF.setText(login);
        passwordTF.setText(password);
    }

    @FXML
    public void initialize() {
        if (!initialized) {
            loginLab.setText(res.getString("login.label"));
            passwordLab.setText(res.getString("password.label"));
            showCB.setText(res.getString("show.check.box"));
            initialized = true;
        }
        deleteBtn.setGraphic(new ImageView(X_LIGHT));
    }

    @FXML
    private void onEditBtnAction() {
        if (!editMode) {
            editMode = true;
            titleTF.setEditable(true);
            loginTF.setEditable(true);
            passwordField.setEditable(true);
            passwordTF.setEditable(true);
            showBorders();
            editBtn.setText(res.getString("save.button"));
        } else {
            editMode = false;
            titleTF.setEditable(false);
            loginTF.setEditable(false);
            passwordField.setEditable(false);
            passwordTF.setEditable(false);
            hideBorders();
            saveData();
            editBtn.setText(res.getString("edit.button"));
        }
    }

    @FXML
    private void onShowCBAction() {
        if (showCB.isSelected()) {
            passwordField.setVisible(false);
            passwordTF.setVisible(true);
        } else {
            passwordField.setVisible(true);
            passwordTF.setVisible(false);
        }
    }

    private void saveData() {
        DBWorker.updateDataForm(this);
    }

    private void showBorders() {
        titleTF.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #5E5E5E;" +
                        "-fx-border-width: 1px");
        loginTF.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #5E5E5E;" +
                        "-fx-border-width: 1px");
        passwordField.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #5E5E5E;" +
                        "-fx-border-width: 1px");
        passwordTF.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #5E5E5E;" +
                        "-fx-border-width: 1px");
    }

    private void hideBorders() {
        titleTF.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 1");
        loginTF.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 1");
        passwordField.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 1");
        passwordTF.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 1");
    }

    @FXML
    private void onDeleteBtnMoving() {
        if (deleteBtn.isHover())
            deleteBtn.setGraphic(new ImageView(X));
        else
            deleteBtn.setGraphic(new ImageView(X_LIGHT));
    }

    @FXML
    private void onDeleteBtnAction() {
        if (!titleTF.getText().isEmpty()
                || !loginTF.getText().isEmpty()
                || !passwordTF.getText().isEmpty())
            if (!Alerts.showConfirm("Are you sure you want to delete this form?"))
                return;

        MainController mainController = ((MainController)SceneSwitcher.getController(SceneSwitcher.Scenes.MAIN));
        assert mainController != null;
        mainController.deleteDataForm(this.id);
    }

    public void setEditMode(boolean em) {
        editMode = !em;
        onEditBtnAction();
    }

    public String getTitle() {
        return titleTF.getText();
    }

    public String getLogin() {
        return loginTF.getText();
    }

    public String getPassword() {
        return passwordTF.getText();
    }

    public int getDFId() {
        return id;
    }

}
