package scenes.main_scene;

import database.DBWorker;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class MainController {

    private static ArrayList<DataForm> SDsList;

    private Button addNewSDBtn;

    @FXML
    private VBox contentBox;

    @FXML
    public void initialize() {
        SDsList = DBWorker.getAllDataForms("user");
        if (!SDsList.isEmpty() && contentBox.getChildren().isEmpty())
            contentBox.getChildren().addAll(SDsList);
        if (addNewSDBtn == null) {
            createAddNewSDBtn();
            contentBox.getChildren().add(addNewSDBtn);
        }
    }

    private void createAddNewSDBtn() {
        addNewSDBtn = new Button("Add a new data form");
        addNewSDBtn.setStyle("-fx-background-color: #E4E4E4; -fx-border-radius: 10px; -fx-text-fill: blue;" +
                " -fx-cursor: hand; -fx-underline: true;");
        addNewSDBtn.setAlignment(Pos.CENTER);
        addNewSDBtn.setOnAction(e -> onAddNewSDListener());
    }

    private void onAddNewSDListener() {
        if (SDsList == null)
            SDsList = new ArrayList<>();
        DataForm newSD = new DataForm();
        SDsList.add(newSD);
        contentBox.getChildren().remove(addNewSDBtn);
        contentBox.getChildren().add(newSD);
        contentBox.getChildren().add(addNewSDBtn);
    }

    public static void setSDsList(ArrayList<DataForm> SDsList) {
        MainController.SDsList = SDsList;
    }
}
