
// import javafx.application.Application;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.stage.Stage;
import java.io.File;
import java.util.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import libs.Card.CardFactory;
import libs.GameWorld.GameWorld;
import libs.Toko.Toko;
import libs.Loader.Loader;

public class MainTestLoad extends Application {

    public void start(Stage primaryStage) throws Exception {
        CardFactory.getInstance();
        Toko.getInstance();
        
        Parent root = FXMLLoader.load(getClass().getResource("view/StartView.fxml"));
        primaryStage.setTitle("Drag and Drop Example");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        // launch(args);
        CardFactory cf = CardFactory.getInstance();
        GameWorld g = GameWorld.getInstance();
        Toko t = Toko.getInstance();
        Loader l = Loader.getInstance();

        Scanner scanner = new Scanner(System.in);
        Loader loader = Loader.getInstance();

        while (true) {
            System.out.println();
            System.out.println("Enter the path to a JAR file to load a plugin, or a file to read (or type 'exit' to quit):");
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                break;
            }

            File file = new File(input);
            if (file.exists()) {
                if (input.endsWith(".jar")) {
                    try {
                        loader.loadJar(input);
                    } catch (Exception e) {
                        System.out.println("Failed to load JAR file: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    loader.loadFile(input);
                }
            } else {
                System.out.println("File does not exist: " + input);
            }

            System.out.println(g.getTurn());
        
            Map<String, Integer> stock = t.getStock();
            stock.forEach((key, value) -> System.out.println(key + " : " + value));
            stock.clear();
            t.setItems(stock);

            System.out.println();
            System.out.println("Game state: ");
            g.setTurn(0);
            stock.clear();
        }
    }
}
