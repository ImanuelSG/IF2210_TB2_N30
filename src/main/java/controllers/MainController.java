package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;

public class MainController {

    @FXML
    private GridPane deckGrid;

    @FXML
    private GridPane fieldGrid;

    private int cardCounter = 3; // To keep track of new card numbers

    @FXML
    public void initialize() {
        setupDragAndDrop();
    }

    private void setupDragAndDrop() {
        for (Node node : deckGrid.getChildren()) {
            if (node instanceof Label) {
                Label label = (Label) node;
                enableDrag(label);
            }
        }

        fieldGrid.setOnDragOver(event -> {
            if (event.getGestureSource() != fieldGrid && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        fieldGrid.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasString()) {
                Node node = (Node) event.getGestureSource();
                deckGrid.getChildren().remove(node);

                int targetCol = (int) (event.getX() / (fieldGrid.getWidth() / fieldGrid.getColumnCount()));
                int targetRow = (int) (event.getY() / (fieldGrid.getHeight() / fieldGrid.getRowCount()));

                fieldGrid.add(node, targetCol, targetRow);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    private void enableDrag(Label label) {
        label.setOnDragDetected(event -> {
            Dragboard db = label.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(label.getText());
            db.setContent(content);
            event.consume();
        });
    }

    @FXML
    private void handleAddCard() {
        Label newCard = new Label("Card " + cardCounter++);
        deckGrid.add(newCard, (cardCounter - 1) % deckGrid.getColumnCount(),
                (cardCounter - 1) / deckGrid.getColumnCount());
        enableDrag(newCard);
    }

    @FXML
    private void handleRemoveCard() {
        if (!deckGrid.getChildren().isEmpty()) {
            Node lastCard = deckGrid.getChildren().get(deckGrid.getChildren().size() - 1);
            deckGrid.getChildren().remove(lastCard);
        }
    }
}
