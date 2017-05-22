package com.ponaguynik.passwordprotector.controller.controllers;


import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.model.DataForm;
import com.ponaguynik.passwordprotector.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**
 * The DataFormController class is a root and controller class for dataform.fxml.
 */
public class DataFormController extends GridPane {

    private static final ResourceBundle RES = ResourceBundle.getBundle("strings.dataform");
    private DataForm dataForm;

    //GUI elements
    @FXML
    private GridPane root;

    @FXML
    private TextField titleTF, loginTF, passwordTF;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label loginLab, passwordLab;

    @FXML
    private CheckBox showCB;

    @FXML
    private Button editBtn, deleteBtn;


    private static boolean initialized = false;

    /**
     * Current mode of the DataFormController (editable or not).
     */
    private boolean editMode = false;

    /**
     * The images for X (delete form) button.
     */
    private static final Image X  =
            new Image(DataFormController.class.getResourceAsStream("/images/x-grey.png"));
    private static final Image X_LIGHT  =
            new Image(DataFormController.class.getResourceAsStream("/images/x-lightgrey.png"));


    /**
     * Load dataform.fxml and set the DataFormController as a root and
     * controller of it. Bind together text properties of Password text field
     * and Password password field.
     */
    DataFormController(DataForm dataForm) {
        this.dataForm = dataForm;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/dataform.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showError(e.getMessage());
            System.exit(1);
        }

        passwordField.textProperty().bindBidirectional(passwordTF.textProperty());
        passwordField.setVisible(true);
        passwordTF.setVisible(false);
        onEditBtnAction();
        viewMode();
        updateFields();
    }

    /**
     * Initialize the DataFrom if it is not initialized.
     */
    @FXML
    public void initialize() {
        if (!initialized) {
            init();
            initialized = true;
        }
    }

    /**
     * Initialize the DataFormController scene. Set "default-theme.css" stylesheet
     * for the root pane. Set text for all elements of the scene from RES.
     */
    private void init() {
        root.getStylesheets().add(getClass().getResource("/styles/default-theme.css").toExternalForm());
        loginLab.setText(RES.getString("login.label"));
        passwordLab.setText(RES.getString("password.label"));
        showCB.setText(RES.getString("show.check.box"));
        titleTF.getStyleClass().add("hide-border");
        loginTF.getStyleClass().add("hide-border");
        passwordTF.getStyleClass().add("hide-border");
        passwordField.getStyleClass().add("hide-border");
        deleteBtn.setGraphic(new ImageView(X_LIGHT));
    }

    @FXML
    private void onEditBtnAction() {
        if (editMode) {
            viewMode();
            saveData();
        } else {
            editMode();
        }
    }

    private void editMode() {
        editMode = true;
        titleTF.setEditable(true);
        loginTF.setEditable(true);
        passwordField.setEditable(true);
        passwordTF.setEditable(true);
        showBorders();
        editBtn.setText(RES.getString("save.button"));
    }

    private void viewMode() {
        editMode = false;
        titleTF.setEditable(false);
        loginTF.setEditable(false);
        passwordField.setEditable(false);
        passwordTF.setEditable(false);
        hideBorders();
        editBtn.setText(RES.getString("edit.button"));
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

    /**
     * Update DataFormController fields from the dataForm.
     */
    private void updateFields() {
        titleTF.setText(dataForm.getTitle());
        loginTF.setText(dataForm.getLogin());
        passwordTF.setText(dataForm.getPassword());
    }

    /**
     * Save data from the fields to the dataForm.
     */
    private void saveInDataForm() {
        dataForm.setTitle(titleTF.getText());
        dataForm.setLogin(loginTF.getText());
        dataForm.setPassword(passwordField.getText());
    }

    /**
     * Save the dataForm to the database.
     */
    private void saveData() {
        try {
            saveInDataForm();
            DBWorker.updateDataForm(dataForm);
        } catch (SQLException e) {
            e.printStackTrace();
            Alerts.showError(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Show borders of the fields.
     */
    private void showBorders() {
        titleTF.getStyleClass().remove("hide-border");
        titleTF.getStyleClass().add("show-border");
        loginTF.getStyleClass().remove("hide-border");
        loginTF.getStyleClass().add("show-border");
        passwordTF.getStyleClass().remove("hide-border");
        passwordTF.getStyleClass().add("show-border");
        passwordField.getStyleClass().remove("hide-border");
        passwordField.getStyleClass().add("show-border");
    }

    /**
     * Hide borders of the fields.
     */
    private void hideBorders() {
        titleTF.getStyleClass().remove("show-border");
        titleTF.getStyleClass().add("hide-border");
        loginTF.getStyleClass().remove("show-border");
        loginTF.getStyleClass().add("hide-border");
        passwordTF.getStyleClass().remove("show-border");
        passwordTF.getStyleClass().add("hide-border");
        passwordField.getStyleClass().remove("show-border");
        passwordField.getStyleClass().add("hide-border");
    }

    /**
     * The moving event listener method for X (delete DataFormController) button. Set X image
     * if it is hovered and set X_LIGHT if it isn't.
     */
    @FXML
    private void onDeleteBtnMoving() {
        if (deleteBtn.isHover())
            deleteBtn.setGraphic(new ImageView(X));
        else
            deleteBtn.setGraphic(new ImageView(X_LIGHT));
    }

    /**
     * The action event listener method for X
     * (delete DataFormController) button.
     */
    @FXML
    private void onDeleteBtnAction() {
        if (!titleTF.getText().isEmpty()
                || !loginTF.getText().isEmpty()
                || !passwordTF.getText().isEmpty()) {
            if (!Alerts.showConfirm("Are you sure you want to delete this form?"))
                return;
        }

        MainController mainController = ((MainController)SceneSwitcher.getController(SceneSwitcher.Scenes.MAIN));
        assert mainController != null;
        mainController.deleteDataForm(dataForm);
    }
}
