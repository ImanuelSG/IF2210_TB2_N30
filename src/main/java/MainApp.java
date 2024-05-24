
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/StartView.fxml"));
        primaryStage.setTitle("MooMoo Adventure");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        String css = this.getClass().getResource("/view/styles.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.show();
        primaryStage.setResizable(false);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
