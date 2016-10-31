package com.ponaguynik.passwordprotector.scenes.main_scene;

import com.ponaguynik.passwordprotector.PasswordProtector;
import com.ponaguynik.passwordprotector.database.DBConnector;
import com.ponaguynik.passwordprotector.database.DBWorker;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainController {

    private static ArrayList<DataForm> dataFormsList;

    private Button addNewDFBtn;

    @FXML
    private VBox contentBox;

    @FXML
    private void initialize() {
        if (!contentBox.getChildren().isEmpty()) {
            contentBox.getChildren().clear();
        }
        if (!dataFormsList.isEmpty())
            contentBox.getChildren().addAll(dataFormsList);
        if (addNewDFBtn == null) {
            createAddNewDFBtn();
            contentBox.getChildren().add(addNewDFBtn);
        } else {
            contentBox.getChildren().remove(addNewDFBtn);
            contentBox.getChildren().add(addNewDFBtn);
        }
    }

    private void createAddNewDFBtn() {
        addNewDFBtn = new Button("Add a new data form");
        addNewDFBtn.setStyle("-fx-background-color: #E4E4E4; -fx-border-radius: 10px; -fx-text-fill: blue;" +
                " -fx-cursor: hand; -fx-underline: true;");
        addNewDFBtn.setAlignment(Pos.CENTER);
        addNewDFBtn.setOnAction(e -> onAddNewDFListener());
    }

    @FXML
    private void onAddNewDFListener() {
        DBWorker.addDataForm(PasswordProtector.currentUser);
        setDataFormsList(DBWorker.getAllDataForms(PasswordProtector.currentUser));
        initialize();
    }

    void deleteDataForm(int id) {
        DBWorker.deleteDataForm(id);
        setDataFormsList(DBWorker.getAllDataForms(PasswordProtector.currentUser));
        initialize();
    }

    public static void setDataFormsList(ArrayList<DataForm> dataFormsList) {
        MainController.dataFormsList = dataFormsList;
    }
}
