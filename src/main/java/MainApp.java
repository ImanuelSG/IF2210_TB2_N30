
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import libs.Card.CardFactory;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/MainView.fxml"));
        primaryStage.setTitle("Drag and Drop Example");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        CardFactory.getInstance();

        System.out.println(CardFactory.createAnimalCard("Ayam"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
