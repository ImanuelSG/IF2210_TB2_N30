import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import libs.Card.CardFactory;
import libs.FileManager.FileManager;
import libs.GameWorld.GameWorld;
import libs.Toko.Toko;

public class MainApp extends Application {

    public static final double WINDOW_WIDTH = 1375;
    private static final double WINDOW_HEIGHT = 1200.0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        CardFactory.getInstance();
        Toko.getInstance();
        GameWorld.getInstance();
        FileManager.getInstance();

        Parent root = FXMLLoader.load(getClass().getResource("view/StartView.fxml"));
        primaryStage.setTitle("MooMoo Adventure");
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // Ensure the window is not resizable
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
