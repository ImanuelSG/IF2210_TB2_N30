package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import libs.Card.Card;
import libs.Card.CardFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ActiveDeckController implements Initializable {

    @FXML
    private HBox container; // Assuming this VBox holds all the BorderPanes

    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private ArrayList<Label> labels = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CardFactory.getInstance();
        populateGrid();

        setCard(1, CardFactory.createAnimalCard("Ayam"));
        setCard(3, CardFactory.createAnimalCard("Beruang"));
        setCard(5, CardFactory.createProductCard("Labu"));
    }

    private void populateGrid() {
        for (int i = 0; i < 6; i++) {
            // Create a new BorderPane
            BorderPane borderPane = new BorderPane();
            borderPane.setStyle(
                    "-fx-background-color: #E2CC9F; -fx-background-radius: 10; -fx-padding: 10; -fx-min-width: 100; -fx-min-height: 150;");

            // Create a VBox for the center position
            VBox vBox = new VBox();
            vBox.setAlignment(javafx.geometry.Pos.CENTER);

            // Create ImageView
            ImageView imageView = new ImageView();
            imageView.setFitWidth(80);
            imageView.setFitHeight(100);
            // Set the imageView to the center of the VBox
            VBox.setMargin(imageView, new Insets(0, 0, 10, 0)); // Add margin to bottom
            vBox.getChildren().add(imageView);

            // Create Label
            Label label = new Label();
            // Set the label to the center of the VBox
            VBox.setMargin(label, new Insets(0, 0, 10, 0)); // Add margin to bottom
            vBox.getChildren().add(label);
            // Set VBox to center of BorderPane
            borderPane.setCenter(vBox);

            // Add BorderPane to container
            container.getChildren().add(borderPane);

            // Add ImageView and Label to lists
            imageViews.add(imageView);
            labels.add(label);

            imageView.setOnDragDetected(event -> {
                Dragboard dragboard = imageView.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                content.putImage(imageView.getImage());
                dragboard.setContent(content);

                event.consume();
            });
        }
    }

    private void setCard(int index, Card card) {
        ImageView imageView = imageViews.get(index);
        Label label = labels.get(index);

        imageView.setImage(card.getImage());
        label.setText(card.getName());
    }

    // Method to add a card to the deck
    public void addCard(Card card) {
        for (int i = 0; i < imageViews.size(); i++) {
            // Check if the current slot is empty
            if (labels.get(i).getText().isEmpty()) {
                setCard(i, card);
                break;
            }
        }
    }

    // Method to remove a card from the deck
    public void removeCard(int index) {
        ImageView imageView = imageViews.get(index);
        Label label = labels.get(index);

        imageView.setImage(null); // Clear the image
        label.setText(""); // Clear the label text
    }
}