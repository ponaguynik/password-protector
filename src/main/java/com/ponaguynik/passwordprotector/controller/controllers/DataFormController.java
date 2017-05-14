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

    private static ResourceBundle res = ResourceBundle.getBundle("strings.dataform");
    private DataForm dataForm;
    private int id;

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
     * Load dataform.fxml and set the DataFormController as root and
     * controller of it. Default mode is "not editable" mode.
     * Bind together text property of Password text field
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
     * Set image for X (delete DataFormController) button.
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
     * for the root pane. Set text for all elements of the scene from res.
     */
    private void init() {
        root.getStylesheets().add(getClass().getResource("/styles/default-theme.css").toExternalForm());
        loginLab.setText(res.getString("login.label"));
        passwordLab.setText(res.getString("password.label"));
        showCB.setText(res.getString("show.check.box"));
        titleTF.getStyleClass().add("hide-border");
        loginTF.getStyleClass().add("hide-border");
        passwordTF.getStyleClass().add("hide-border");
        passwordField.getStyleClass().add("hide-border");
        deleteBtn.setGraphic(new ImageView(X_LIGHT));
    }

    /**
     * If editMode = false: set editMode = true, set editable
     * mode for Title, Login and Password fields, show borders,
     * set "save" text for edit button. And vise versa
     * if editMode = true.
     */
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
        editBtn.setText(res.getString("save.button"));
    }

    private void viewMode() {
        editMode = false;
        titleTF.setEditable(false);
        loginTF.setEditable(false);
        passwordField.setEditable(false);
        passwordTF.setEditable(false);
        hideBorders();
        editBtn.setText(res.getString("edit.button"));
    }

    /**
     * The action event listener method for 'Show' check box.
     * If 'Show' is selected - show Password text field and
     * hide Password password field. And vise versa if 'Show'
     * isn't selected.
     */
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
     * Update DataFormController fields from dataForm.
     */
    private void updateFields() {
        id = dataForm.getId();
        titleTF.setText(dataForm.getTitle());
        loginTF.setText(dataForm.getLogin());
        passwordTF.setText(dataForm.getPassword());
    }

    private void saveInDataForm() {
        dataForm.setTitle(titleTF.getText());
        dataForm.setLogin(loginTF.getText());
        dataForm.setPassword(passwordField.getText());
    }

    /**
     * Save data form to the database.
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
     * (delete DataFormController) button. Ask the user
     * whether it wants to delete this form.
     * Delete DataFormController if true.
     */
    @FXML
    private void onDeleteBtnAction() {
        if (!titleTF.getText().isEmpty()
                || !loginTF.getText().isEmpty()
                || !passwordTF.getText().isEmpty())
            if (!Alerts.showConfirm("Are you sure you want to delete this form?"))
                return;

        MainController mainController = ((MainController)SceneSwitcher.getController(SceneSwitcher.Scenes.MAIN));
        assert mainController != null;
        mainController.deleteDataForm(dataForm);
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
        onEditBtnAction();
    }

    public boolean getEditMode() {
        return editMode;
    }
}
