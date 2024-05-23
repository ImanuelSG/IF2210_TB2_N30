package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import libs.Card.CardFactory;
import libs.Card.Harvestable.AnimalCard;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LadangkuController implements Initializable {

    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CardFactory.getInstance();
        populateGrid();

    }

    private void populateGrid() {
        ArrayList<String> AnimalString = new ArrayList<>();
        AnimalString.add("Ayam");
        AnimalString.add("Sapi");
        AnimalString.add("Domba");
        AnimalString.add("Beruang");
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

                AnimalCard animalCard = CardFactory.createAnimalCard(AnimalString.get(i));

                // Set the image and name
                imageView.setImage(animalCard.getImage());
                label.setText(animalCard.getName());

                // Add the BorderPane to the gridPane
                gridPane.add(borderPane, j, i);
            }
        }
    }

    private void setCardAt(int row, int col, String name, String imagePath) {
        // Get the BorderPane at the specified row and column
        BorderPane borderPane = (BorderPane) gridPane.getChildren().get(row * 5 + col);

        // Get the VBox from the center of the BorderPane
        VBox vBox = (VBox) borderPane.getCenter();

        // Get the ImageView and Label from the VBox
        ImageView imageView = (ImageView) vBox.getChildren().get(0);
        Label label = (Label) vBox.getChildren().get(1);

        // Set the image and name
        imageView.setImage(new javafx.scene.image.Image(imagePath));
        label.setText(name);
    }
}
