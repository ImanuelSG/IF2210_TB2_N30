package libs.Card;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CardDisplay extends Application {
    private Card card;

    public CardDisplay() {

    }

    public CardDisplay(Card card) {
        this.card = card;
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Card Display");

        // Load image from resources folder
        Image image = new Image(getClass().getResourceAsStream("/1200px-Ayam_Pelung.jpg"));

        // Check if the image is loaded successfully
        if (image != null) {
            System.out.println("Image loaded successfully.");
            System.out.println("Image Path: " + getClass().getResource("/1200px-Ayam_Pelung.jpg").getPath());
        } else {
            System.out.println("Failed to load the image.");
        }

        this.card = new Card("Ayam", image);

        // Check if the card object is already initialized
        if (card == null) {
            // If not initialized, create a new card object
            this.card = new Card("Ayam", image);
        }

        // System.out.println(this.card.get);

        // Create a VBox to hold the components
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        // Create and add Text for the card's name
        Text nameText = new Text(card.getName());
        nameText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // Create and add ImageView for the card's image
        ImageView imageView = new ImageView();
        imageView.setImage(card.getImage()); // Set the image directly
        // Set fit width and height properties to adjust image size if necessary
        imageView.setFitWidth(200); // Adjust the width as needed
        imageView.setFitHeight(200); // Adjust the height as needed



        // Add components to the VBox
        vbox.getChildren().addAll(nameText, imageView);

        // Set up the scene
        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        Scene scene = new Scene(root, 300, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        // Create a CardDisplay with the card
        launch(args);
    }
}
