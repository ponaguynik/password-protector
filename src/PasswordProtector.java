import database.DBConnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PasswordProtector extends Application {

    public static String currentUser = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        DBConnector.makeConnection();
        Parent root = FXMLLoader.load(getClass().getResource("scenes/login_scene/login.fxml"));
        primaryStage.setTitle("PasswordProtector");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
