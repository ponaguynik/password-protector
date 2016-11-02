package com.ponaguynik.passwordprotector.scenes.main_scene;

import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.database.DBWorker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class DataForm extends GridPane {

    @FXML
    private CheckBox showCB;
    @FXML
    private Button editBtn, deleteBtn;
    @FXML
    private TextField titleTF, loginTF, passwordTF;
    @FXML
    private PasswordField passwordField;

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
            editBtn.setText("Save");
        } else {
            editMode = false;
            titleTF.setEditable(false);
            loginTF.setEditable(false);
            passwordField.setEditable(false);
            passwordTF.setEditable(false);
            hideBorders();
            saveData();
            editBtn.setText("Edit");
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
