
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import libs.Card.CardFactory;
import libs.Toko.Toko;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        CardFactory.getInstance();
        Toko.getInstance();
        
        Parent root = FXMLLoader.load(getClass().getResource("view/StartView.fxml"));
        primaryStage.setTitle("Drag and Drop Example");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
