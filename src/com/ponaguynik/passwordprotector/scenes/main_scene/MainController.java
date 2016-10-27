package com.ponaguynik.passwordprotector.scenes.main_scene;

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
    public void initialize() throws SQLException {
        if (!contentBox.getChildren().isEmpty()) {
            contentBox.getChildren().clear();
        }
        if (!dataFormsList.isEmpty())
            contentBox.getChildren().addAll(dataFormsList);
        if (addNewDFBtn == null) {
            createAddNewDFBtn();
            contentBox.getChildren().add(addNewDFBtn);
        }
    }

    private void createAddNewDFBtn() {
        addNewDFBtn = new Button("Add a new data form");
        addNewDFBtn.setStyle("-fx-background-color: #E4E4E4; -fx-border-radius: 10px; -fx-text-fill: blue;" +
                " -fx-cursor: hand; -fx-underline: true;");
        addNewDFBtn.setAlignment(Pos.CENTER);
        addNewDFBtn.setOnAction(e -> onAddNewSDListener());
    }

    private void onAddNewSDListener() {
        if (dataFormsList == null)
            dataFormsList = new ArrayList<>();
        DataForm newSD = new DataForm();
        dataFormsList.add(newSD);
        contentBox.getChildren().remove(addNewDFBtn);
        contentBox.getChildren().add(newSD);
        contentBox.getChildren().add(addNewDFBtn);
    }

    public static void setDataFormsList(ArrayList<DataForm> dataFormsList) {
        MainController.dataFormsList = dataFormsList;
    }
}
