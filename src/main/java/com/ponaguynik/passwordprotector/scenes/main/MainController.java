package com.ponaguynik.passwordprotector.scenes.main;

/**
 * The MainController class is a controller class
 * for main.fxml (Main scene).
 */

import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.database.DBWorker;
import com.ponaguynik.passwordprotector.other.MenuHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController {

    /**
     * ResourceBundle object that contains strings of the
     * main.properties file.
     */
    private static ResourceBundle res = ResourceBundle.getBundle("strings.main");

    private static ArrayList<DataForm> dataFormsList;

    /**
     * The root pane of the Main scene.
     */
    @FXML
    private BorderPane root;

    //Menu
    /**
     * File, Settings and Help menu.
     */
    @FXML
    private Menu fileMenu, settingsMenu, helpMenu;
    //Menu items
        //File
        /**
         * Change user and Exit menu items.
         */
        @FXML
        private MenuItem changeUserItem, exitItem;
        //Settings
        /**
         * Change keyword and Delete account menu items.
         */
        @FXML
        private MenuItem changeKeyItem, deleteAccItem;
        //Help
        /**
         * About menu item.
         */
        @FXML
        private MenuItem aboutItem;
    //End menu items

    /**
     * Add (+) button.
     */
    private Button addBtn;

    /**
     * PasswordProtector label.
     */
    @FXML
    private Label passProtLab;
    /**
     * Content VBox.
     */
    @FXML
    private VBox contentBox;

    /**
     * Dark plus and light plus images for Add button.
     * PasswordProtector image for PasswordProtector label.
     */
    //Images
    private static final Image PLUS  = new Image(MainController.class.getResourceAsStream("/images/plus-grey.png"));
    private static final Image PLUS_LIGHT  = new Image(MainController.class.getResourceAsStream("/images/plus-lightgrey.png"));
    private static final Image PASSWORD_PROTECTOR  = new Image(MainController.class.getResourceAsStream("/images/password-protector-36.png"));

    private static boolean initialized = false;

    /**
     * If it's not initialized than invoke init() method.
     * If a content box isn't empty than clear it.
     * Add all data forms from data forms list to the content box.
     * Create Add button and add it to the content box.
     */
    @FXML
    private void initialize() {
        if (!initialized) {
            init();
            initialized = true;
        }
        if (!contentBox.getChildren().isEmpty()) {
            contentBox.getChildren().clear();
        }
        if (dataFormsList != null && !dataFormsList.isEmpty())
            contentBox.getChildren().addAll(dataFormsList);
        if (addBtn == null)
            createAddBtn();
        contentBox.getChildren().add(addBtn);
    }

    /**
     * Initialize the Main scene. Set "default-theme.css" stylesheet
     * for root pane. Set text for all elements of the scene from res.
     * Set PasswordProtector image on PasswordProtector label.
     */
    private void init() {
        root.getStylesheets().add(getClass().getResource("/styles/default-theme.css").toExternalForm());
        passProtLab.setGraphic(new ImageView(PASSWORD_PROTECTOR));
        fileMenu.setText(res.getString("file.menu"));
        settingsMenu.setText(res.getString("settings.menu"));
        helpMenu.setText(res.getString("help.menu"));
        changeUserItem.setText(res.getString("change.account.item"));
        exitItem.setText(res.getString("exit.item"));
        changeKeyItem.setText(res.getString("change.key.item"));
        deleteAccItem.setText(res.getString("delete.account.item"));
        aboutItem.setText(res.getString("about.item"));
    }

    /**
     * The action event listener method for menu items.
     * It is using MenuHelper class.
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
     * The action event listener method for Add button.
     * Add a new data form to a database and set data forms list from the database.
     * Invoke initialize() method.
     */
    private void onAddBtnAction() {
        DBWorker.addDataForm(PasswordProtector.currentUser);
        setDataFormsList(DBWorker.getAllDataForms(PasswordProtector.currentUser));
        initialize();
    }

    /**
     * The moving event listener method for Add button. Set PLUS image
     * if it is hovered and set PLUS_LIGHT if it isn't.
     */
    private void onAddBtnMoving() {
        if (addBtn.isHover())
            addBtn.setGraphic(new ImageView(PLUS));
        else
            addBtn.setGraphic(new ImageView(PLUS_LIGHT));
    }

    /**
     * Delete data form from the database by id.
     * Set data forms list from the database.
     * Invoke initialize() method.
     */
    void deleteDataForm(int id) {
        DBWorker.deleteDataForm(id);
        setDataFormsList(DBWorker.getAllDataForms(PasswordProtector.currentUser));
        initialize();
    }

    public static void setDataFormsList(ArrayList<DataForm> dataFormsList) {
        MainController.dataFormsList = dataFormsList;
    }

    /**
     * Invoke initialize() method.
     */
    public void reset() {
        initialize();
    }
}
