package com.ponaguynik.passwordprotector.scenes.main_scene;

import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.database.DBWorker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class MainController {

    private static ArrayList<DataForm> dataFormsList;

    //Menu items
        //File
        @FXML
        private MenuItem changeUserItem, exitItem;
        //Settings
            //Language
            @FXML
            private MenuItem englishItem, russianItem;
        @FXML
        private MenuItem changeKeyItem, deleteAccItem;
        //Help
        @FXML
        private MenuItem aboutItem;
    //End menu items

    private Button addBtn;

    @FXML
    private VBox contentBox;


    //Images
    private static final Image PLUS  = new Image(MainController.class.getResourceAsStream("../../res/images/plus-grey.png"));
    private static final Image PLUS_LIGHT  = new Image(MainController.class.getResourceAsStream("../../res/images/plus-lightgrey.png"));

    @FXML
    private void initialize() {
        if (!contentBox.getChildren().isEmpty()) {
            contentBox.getChildren().clear();
        }
        if (dataFormsList != null && !dataFormsList.isEmpty())
            contentBox.getChildren().addAll(dataFormsList);
        if (addBtn == null)
            createAddBtn();
        contentBox.getChildren().add(addBtn);
    }

    @FXML
    private void onMenuItemsAction(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        if (item == changeUserItem)
            MenuHelper.changeUser();
        else if (item == exitItem)
            MenuHelper.exit();
        else if (item == englishItem)
            MenuHelper.english();
        else if (item == russianItem)
            MenuHelper.russian();
        else if (item == changeKeyItem)
            MenuHelper.changeKeyword();
        else if (item == deleteAccItem)
            MenuHelper.deleteAccount();
        else if (item == aboutItem)
            MenuHelper.about();
    }

    private void createAddBtn() {
        addBtn = new Button();
        addBtn.setGraphic(new ImageView(PLUS_LIGHT));
        addBtn.setAlignment(Pos.CENTER);
        addBtn.setOnAction(e -> onAddBtnAction());
        addBtn.setOnMouseEntered(e -> onAddBtnMoving());
        addBtn.setOnMouseExited(e -> onAddBtnMoving());
    }

    private void onAddBtnAction() {
        DBWorker.addDataForm(PasswordProtector.currentUser);
        setDataFormsList(DBWorker.getAllDataForms(PasswordProtector.currentUser));
        initialize();
    }

    private void onAddBtnMoving() {
        if (addBtn.isHover())
            addBtn.setGraphic(new ImageView(PLUS));
        else
            addBtn.setGraphic(new ImageView(PLUS_LIGHT));
    }

    void deleteDataForm(int id) {
        DBWorker.deleteDataForm(id);
        setDataFormsList(DBWorker.getAllDataForms(PasswordProtector.currentUser));
        initialize();
    }

    public static void setDataFormsList(ArrayList<DataForm> dataFormsList) {
        MainController.dataFormsList = dataFormsList;
    }

    public void reset() {
        initialize();
    }
}
