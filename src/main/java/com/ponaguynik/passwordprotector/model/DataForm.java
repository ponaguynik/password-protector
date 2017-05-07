package com.ponaguynik.passwordprotector.model;

/**
 * The DataForm class is a root and controller class for dataform.view.fxml.
 * It is a GridPane that stores fields for user's data (title, login, password).
 */

import com.ponaguynik.passwordprotector.SceneSwitcher;
import com.ponaguynik.passwordprotector.controller.controllers.MainController;
import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ResourceBundle;

public class DataForm extends GridPane {

    /**
     * ResourceBundle object that contains strings of the
     * dataform.properties file.
     */
    private static ResourceBundle res = ResourceBundle.getBundle("strings.dataform");

    /**
     * The root pane for DataForm scene.
     */
    @FXML
    private GridPane root;
    /**
     * Title, Login and Password text fields.
     */
    @FXML
    private TextField titleTF, loginTF, passwordTF;
    /**
     * Password password field.
     */
    @FXML
    private PasswordField passwordField;
    /**
     * Labels for Login and Password fields.
     */
    @FXML
    private Label loginLab, passwordLab;
    /**
     * Show check box.
     */
    @FXML
    private CheckBox showCB;
    /**
     * Edit and X (delete form) buttons.
     */
    @FXML
    private Button editBtn, deleteBtn;

    /**
     * Whether the DataForm has been initialized.
     */
    private static boolean initialized = false;

    /**
     * Current mode of the DataForm (editable or not).
     */
    private boolean editMode;

    /**
     * Id of the DataForm.
     */
    private final int id;

    /**
     * The images for X (delete form) button.
     */
    private static final Image X  = new Image(DataForm.class.getResourceAsStream("/images/x-grey.png"));
    private static final Image X_LIGHT  = new Image(DataForm.class.getResourceAsStream("/images/x-lightgrey.png"));


    /**
     * Load dataform.view.fxml and set the DataForm as root and
     * controller of it. Default mode is "not editable" mode.
     * Bind together text property of Password text field
     * and Password password field.
     *
     * @param id of the DataForm.
     */
    public DataForm(int id) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/dataform.fxml"));
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

    /**
     * Invoke DataFrom(int id) constructor. Set text of Title,
     * Login and Password text fields as "title", "login" and
     * "password" respectively.
     */
    public DataForm(int id, String title, String login, String password) {
        this(id);
        titleTF.setText(title);
        loginTF.setText(login);
        passwordTF.setText(password);
    }

    /**
     * Initialize the DataFrom if it is not initialized.
     * Set image for X (delete DataForm) button.
     */
    @FXML
    public void initialize() {
        if (!initialized) {
            init();
            initialized = true;
        }
        deleteBtn.setGraphic(new ImageView(X_LIGHT));
    }

    /**
     * Initialize the DataForm scene. Set "default-theme.css" stylesheet
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
    }

    /**
     * The action event listener method for 'Edit' button.
     * If editMode = false: set editMode = true, set editable
     * mode for Title, Login and Password fields, show borders,
     * set "save" text for edit button. And vise versa
     * if editMode = true.
     */
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
     * Save all data of fields to the database.
     */
    private void saveData() {
        DBWorker.updateDataForm(this);
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
     * The moving event listener method for X
     * (delete DataForm) button. Set X image
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
     * (delete DataForm) button. Ask the user
     * whether it wants to delete this form.
     * Delete DataForm if true.
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
