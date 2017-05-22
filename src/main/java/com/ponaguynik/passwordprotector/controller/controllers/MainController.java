package com.ponaguynik.passwordprotector.controller.controllers;


import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.controller.main.MenuHelper;
import com.ponaguynik.passwordprotector.model.DataForm;
import com.ponaguynik.passwordprotector.util.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The MainController class is a controller class
 * for main.fxml (Main scene).
 */
public class MainController {

    private static final ResourceBundle RES = ResourceBundle.getBundle("strings.main");
    private static ArrayList<DataForm> dataFormsList;

    //Images
    private static final Image PLUS  =
            new Image(MainController.class.getResourceAsStream("/images/plus-grey.png"));
    private static final Image PLUS_LIGHT  =
            new Image(MainController.class.getResourceAsStream("/images/plus-lightgrey.png"));
    private static final Image PASSWORD_PROTECTOR  =
            new Image(MainController.class.getResourceAsStream("/images/password-protector-36.png"));

    //GUI elements
    @FXML
    private BorderPane root;

    //Menu
    @FXML
    private Menu fileMenu, settingsMenu, helpMenu;
    //Menu items
        //File
        @FXML
        private MenuItem changeUserItem, exitItem;
        //Settings
        @FXML
        private MenuItem changeKeyItem, deleteAccItem;
        //Help
        @FXML
        private MenuItem aboutItem;
    //End menu items

    //Add (+) button.
    private Button addBtn;

    @FXML
    private Label passProtLab;

    @FXML
    private VBox contentBox;

    /**
     * Initialize the Main scene. Set "default-theme.css" stylesheet
     * for root pane. Set text for all elements of the scene from RES.
     * Set PasswordProtector image on PasswordProtector label.
     */
    @FXML
    private void initialize() {
        root.getStylesheets().add(getClass().getResource("/styles/default-theme.css").toExternalForm());
        passProtLab.setGraphic(new ImageView(PASSWORD_PROTECTOR));
        fileMenu.setText(RES.getString("file.menu"));
        settingsMenu.setText(RES.getString("settings.menu"));
        helpMenu.setText(RES.getString("help.menu"));
        changeUserItem.setText(RES.getString("change.account.item"));
        exitItem.setText(RES.getString("exit.item"));
        changeKeyItem.setText(RES.getString("change.key.item"));
        deleteAccItem.setText(RES.getString("delete.account.item"));
        aboutItem.setText(RES.getString("about.item"));
    }

    /**
     * Update Data Forms from database.
     * Add 'Add' button to the end.
     */
    public void update() {
        try {
            dataFormsList = DBWorker.getAllDataForms(PasswordProtector.currentUser);
        } catch (SQLException e) {
            e.printStackTrace();
            Alerts.showError(e.getMessage());
            System.exit(1);
        }
        if (!contentBox.getChildren().isEmpty()) {
            contentBox.getChildren().clear();
        }
        if (dataFormsList != null && !dataFormsList.isEmpty()) {
            contentBox.getChildren().addAll(convertToDataFormControllers(dataFormsList));
        }
        if (addBtn == null)
            createAddBtn();
        contentBox.getChildren().add(addBtn);
    }

    /**
     * MenuHelper class is used.
     */
    @FXML
    private void onMenuItemsAction(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        if (item == changeUserItem)
            MenuHelper.changeAccount();
        else if (item == exitItem)
            MenuHelper.exit();
        else if (item == changeKeyItem)
            MenuHelper.changeKeyword();
        else if (item == deleteAccItem)
            MenuHelper.deleteAccount();
        else if (item == aboutItem)
            MenuHelper.about();
    }

    /**
     * Create a new Add button (+).
     */
    private void createAddBtn() {
        addBtn = new Button();
        addBtn.getStyleClass().add("button-blue");
        addBtn.setGraphic(new ImageView(PLUS_LIGHT));
        addBtn.setAlignment(Pos.CENTER);
        addBtn.setOnAction(e -> onAddBtnAction());
        addBtn.setOnMouseEntered(e -> onAddBtnMoving());
        addBtn.setOnMouseExited(e -> onAddBtnMoving());
    }

    /**
     * Add a new data form to the database and set data forms list from the database.
     * Invoke reset().
     */
    private void onAddBtnAction() {
        try {
            DBWorker.addEmptyDataForm(PasswordProtector.currentUser);
            update();
        } catch (SQLException e) {
            e.printStackTrace();
            Alerts.showError(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Set PLUS image if it is hovered and set PLUS_LIGHT if it isn't.
     */
    private void onAddBtnMoving() {
        if (addBtn.isHover())
            addBtn.setGraphic(new ImageView(PLUS));
        else
            addBtn.setGraphic(new ImageView(PLUS_LIGHT));
    }

    /**
     * Delete data form from the database by id.
     * Invoke update().
     */
    void deleteDataForm(DataForm dataForm) {
        try {
            DBWorker.deleteDataForm(dataForm);
            update();
        } catch (SQLException e) {
            e.printStackTrace();
            Alerts.showError(e.getMessage());
            System.exit(1);
        }
    }

    private ArrayList<DataFormController> convertToDataFormControllers(ArrayList<DataForm> dataForms) {
        ArrayList<DataFormController> dataFormControllers = new ArrayList<>();

        for (DataForm dataForm : dataForms) {
            DataFormController dataFormController = new DataFormController(dataForm);
            dataFormControllers.add(dataFormController);
        }

        return dataFormControllers;
    }
}
