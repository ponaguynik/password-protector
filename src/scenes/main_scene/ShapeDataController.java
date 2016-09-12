package scenes.main_scene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ShapeDataController extends GridPane {

    @FXML
    private CheckBox showCB;
    @FXML
    private Button editBtn;
    @FXML
    private TextField nameTF, usernameTF, passwordTF;
    @FXML
    private PasswordField passwordField;

    private boolean editMode;

    public ShapeDataController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/main_scene/shape_data.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        editMode = false;
        passwordField.textProperty().bindBidirectional(passwordTF.textProperty());
        passwordField.setVisible(true);
        passwordTF.setVisible(false);
        onEditListener();
    }

    @FXML
    private void onEditListener() {
        if (!editMode) {
            editMode = true;
            nameTF.setEditable(true);
            usernameTF.setEditable(true);
            passwordField.setEditable(true);
            passwordTF.setEditable(true);
            showBorders();
            editBtn.setText("Save");
        } else {
            editMode = false;
            nameTF.setEditable(false);
            usernameTF.setEditable(false);
            passwordField.setEditable(false);
            passwordTF.setEditable(false);
            hideBorders();
            editBtn.setText("Edit");
        }
    }

    @FXML
    private void onShowListener() {
        if (showCB.isSelected()) {
            passwordField.setVisible(false);
            passwordTF.setVisible(true);
        } else {
            passwordField.setVisible(true);
            passwordTF.setVisible(false);
        }
    }

    private void showBorders() {
        nameTF.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #5E5E5E;" +
                        "-fx-border-width: 1px");
        usernameTF.setStyle(
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
        nameTF.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 1");
        usernameTF.setStyle(
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

    public boolean isEditMode() {
        return editMode;
    }
}
