package libs.Field;

import javafx.scene.layout.GridPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import libs.Interfaces.Harvestable;

public class FieldObserver extends GridPane {
    private Field field;

    public FieldObserver(Field field) {
        this.field = field;
        int rows = field.getField().length;
        int cols = field.getField()[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                final int row = i;
                final int col = j;

                ImageView cellView = new ImageView();
                field.getField()[i][j].addListener((obs, oldHarvestable, newHarvestable) -> {
                    if (newHarvestable != null) {
                        cellView.setImage(newHarvestable.getImage());
                    } else {
                        cellView.setImage(null);
                    }
                });

                cellView.setOnDragOver(event -> {
                    if (event.getGestureSource() != cellView && event.getDragboard().hasImage()) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();
                });

                cellView.setOnDragDropped(event -> {
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    if (db.hasImage()) {
                        // Implement your logic to get the dragged card and place it in the field
                        Harvestable draggedCard = ... // Obtain the dragged card
                        field.setHarvestable(row, col, draggedCard);
                        success = true;
                    }
                    event.setDropCompleted(success);
                    event.consume();
                });

                this.add(cellView, j, i);
            }
        }
    }
}
