package controller;

import java.net.URL;
import java.util.ResourceBundle;

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
import libs.Field.Ladang;
import libs.GameWorld.GameWorld;
import libs.Player.Player;

public class LadangMusuhController implements Initializable, Observer {

    @FXML
    private GridPane gridPane;

    private Ladang ladang;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameWorld.getInstance().addObserver(this);
        ladang = GameWorld.getInstance().getEnemy().getField();
        populateGrid();
        updateView();
    }

    private void populateGrid() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                BorderPane borderPane = createGridCell();
                gridPane.add(borderPane, j, i);
            }
        }
    }

    private BorderPane createGridCell() {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle(
                "-fx-background-color: #E2CC9F; -fx-background-radius: 10; -fx-padding: 10; -fx-min-width: 100; -fx-min-height: 150;");

        VBox vBox = new VBox();
        vBox.setAlignment(javafx.geometry.Pos.CENTER);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(80);
        imageView.setFitHeight(100);
        VBox.setMargin(imageView, new Insets(0, 0, 10, 0));
        vBox.getChildren().add(imageView);

        Label label = new Label();
        VBox.setMargin(label, new Insets(0, 0, 10, 0));
        vBox.getChildren().add(label);

        borderPane.setCenter(vBox);

        borderPane.setOnDragOver(event -> handleDragOver(event, borderPane));
        borderPane.setOnDragExited(event -> handleDragExited(event, borderPane));
        borderPane.setOnDragDropped(event -> handleDragDropped(event, borderPane));

        return borderPane;
    }

    private void handleDragOver(DragEvent event, BorderPane borderPane) {
        if (event.getGestureSource() != gridPane && event.getDragboard().hasString()) {
            borderPane.setStyle(
                    "-fx-background-color: yellow; -fx-border-color: red; -fx-border-width: 3px; -fx-border-style: dashed;");
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    private void handleDragExited(DragEvent event, BorderPane borderPane) {
        borderPane.setStyle(
                "-fx-background-color: #E2CC9F; -fx-background-radius: 10; -fx-padding: 10; -fx-min-width: 100; -fx-min-height: 150;");
        event.consume();
    }

    private void handleDragDropped(DragEvent event, BorderPane borderPane) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {

            int columnIndex = GridPane.getColumnIndex(borderPane);
            int rowIndex = GridPane.getRowIndex(borderPane);
            if (ladang.getHarvestable(rowIndex, columnIndex) == null
                    && CardFactory.isValidHarvestableCard(db.getString())) {

                ImageView imageView = (ImageView) ((VBox) borderPane.getCenter()).getChildren().get(0);
                Label label = (Label) ((VBox) borderPane.getCenter()).getChildren().get(1);
                HarvestableCard card = CardFactory.createHarvestableCard(db.getString());
                imageView.setImage(card.getImage());
                label.setText(card.getName());
                ladang.setHarvestable(rowIndex, columnIndex, card);
                success = true;
            }
            updateView();
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @Override
    public void updateView() {
        GameWorld gameWorld = GameWorld.getInstance();
        Player enemy = gameWorld.getEnemy();
        Ladang enemy_field = enemy.getField();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                HarvestableCard card = enemy_field.getHarvestable(i, j);
                if (card != null) {
                    BorderPane borderPane = (BorderPane) gridPane.getChildren().get(i * 5 + j);
                    ImageView imageView = (ImageView) ((VBox) borderPane.getCenter()).getChildren().get(0);
                    Label label = (Label) ((VBox) borderPane.getCenter()).getChildren().get(1);
                    imageView.setImage(card.getImage());
                    label.setText(card.getName());
                } else {
                    BorderPane borderPane = (BorderPane) gridPane.getChildren().get(i * 5 + j);
                    ImageView imageView = (ImageView) ((VBox) borderPane.getCenter()).getChildren().get(0);
                    Label label = (Label) ((VBox) borderPane.getCenter()).getChildren().get(1);
                    imageView.setImage(null);
                    label.setText("");
                }
            }
        }
    }
}
