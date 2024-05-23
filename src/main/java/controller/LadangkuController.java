package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import libs.Card.CardFactory;
import libs.Card.Harvestable.HarvestableCard;

import java.net.URL;
import java.util.ResourceBundle;

public class LadangkuController implements Initializable {

    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CardFactory.getInstance();
        populateGrid();
        enableGridDrop();
    }

    private void populateGrid() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
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

                // Set the VBox to the center of the BorderPane
                borderPane.setCenter(vBox);

                borderPane.setOnDragOver(new EventHandler<DragEvent>() {

                    public void handle(DragEvent event) {

                        /* data is dragged over the target */
                        if (event.getGestureSource() != gridPane &&
                                event.getDragboard().hasString()) {
                            borderPane.setStyle(
                                    "-fx-background-color: yellow; -fx-border-color: red; -fx-border-width: 3px; -fx-border-style: dashed;");
                            /* allow for both copying and moving, whatever user chooses */
                            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        }
                        event.consume();
                    }
                });

                borderPane.setOnDragExited(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        /* mouse moved away, remove the graphical cues */
                        borderPane.setStyle(
                                "-fx-background-color: #E2CC9F; -fx-background-radius: 10; -fx-padding: 10; -fx-min-width: 100; -fx-min-height: 150;");
                        event.consume();
                    }
                });

                borderPane.setOnDragDropped(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {

                        /* data dropped */
                        /* if there is a string data on dragboard, read it and use it */
                        Dragboard db = event.getDragboard();
                        boolean success = false;
                        if (db.hasString()) {
                            imageView.setImage(db.getImage());
                            label.setText(db.getString());

                            success = true;
                        }
                        /*
                         * let the source know whether the string was successfully
                         * transferred and used
                         */
                        event.setDropCompleted(success);

                        event.consume();
                    }
                });

                // Add the BorderPane to the gridPane
                gridPane.add(borderPane, j, i);
            }
        }
    }

    private void setCardAt(int row, int col, HarvestableCard card) {
        // Get the BorderPane at the specified row and column
        BorderPane borderPane = (BorderPane) gridPane.getChildren().get(row * 5 + col);

        // Get the VBox from the center of the BorderPane
        VBox vBox = (VBox) borderPane.getCenter();

        // Get the ImageView and Label from the VBox
        ImageView imageView = (ImageView) vBox.getChildren().get(0);
        Label label = (Label) vBox.getChildren().get(1);

        // Set the image and name
        imageView.setImage(card.getImage());
        label.setText(card.getName());
    }

    private void enableGridDrop() {

        // Set an event handler to handle drop onto the grid
        gridPane.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {

                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    // Get the card name from the dragboard
                    String cardName = db.getString();
                    // Create the corresponding card from the name (assuming CardFactory provides
                    // this functionality)
                    HarvestableCard card = CardFactory.createAnimalCard(cardName);
                    // Get the row and column where the drop occurred
                    int col = (int) event.getX() / 100; // Adjust this value according to your cell width
                    int row = (int) event.getY() / 150; // Adjust this value according to your cell height
                    // Set the card at the specified row and column
                    setCardAt(row, col, card);
                    success = true;
                }
                /*
                 * let the source know whether the string was successfully
                 * transferred and used
                 */
                event.setDropCompleted(success);

                event.consume();
            }
        });
    }

    

}
