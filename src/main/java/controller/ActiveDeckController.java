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
import libs.Card.Harvestable.HarvestableCard;
import libs.Card.Products.ProductCard;
import libs.Card.Useable.Useable;
import libs.Deck.ActiveDeck;
import libs.GameWorld.GameWorld;

import java.net.URL;
import java.util.ResourceBundle;

public class ActiveDeckController implements Initializable, Observer {

    @FXML
    private HBox container; // Assuming this VBox holds all the BorderPanes

    private ActiveDeck activeDeck;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        activeDeck = GameWorld.getInstance().getCurrentPlayer().getActiveDeck();
        GameWorld.getInstance().addObserver(this);
        populateGrid();
        updateView();
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

            imageView.setOnDragDetected(event -> {
                Dragboard dragboard = imageView.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                content.putImage(imageView.getImage());

                Card curr = activeDeck.getCard(container.getChildren().indexOf(borderPane));
                String type = "";
                if (curr instanceof HarvestableCard) {
                    type = "Harvestable";
                } else if (curr instanceof ProductCard) {
                    type = "Product";
                } else if (curr instanceof Useable) {
                    type = "Useable";
                }

                content.putString(label.getText() + "_" + type);

                dragboard.setContent(content);

                event.consume();

            });

            imageView.setOnDragDone(event -> {

                if (event.isDropCompleted()) {

                    activeDeck.removeCard(container.getChildren().indexOf(borderPane));
                    updateView();
                }
            });

        }
    }

    private void setCard(int index, Card card) {
        ImageView imageView = (ImageView) ((VBox) ((BorderPane) container.getChildren().get(index)).getCenter())
                .getChildren().get(0);
        Label label = (Label) ((VBox) ((BorderPane) container.getChildren().get(index)).getCenter()).getChildren()
                .get(1);

        imageView.setImage(card.getImage());
        label.setText(card.getName());
    }

    public void removeCardAt(int index) {
        ImageView imageView = (ImageView) ((VBox) ((BorderPane) container.getChildren().get(index)).getCenter())
                .getChildren().get(0);
        Label label = (Label) ((VBox) ((BorderPane) container.getChildren().get(index)).getCenter()).getChildren()
                .get(1);

        imageView.setImage(null);
        label.setText("");
    }

    // Method to remove a card from the deck

    @Override
    public void updateView() {
        activeDeck = GameWorld.getInstance().getCurrentPlayer().getActiveDeck();
        for (int i = 0; i < 6; i++) {
            Card card = activeDeck.getCard(i);
            if (card != null) {
                setCard(i, card);
            } else {
                removeCardAt(i);
            }
        }
    }
}