// package libs.Field;

// import javafx.scene.layout.GridPane;
// import javafx.scene.image.ImageView;
// import javafx.scene.input.ClipboardContent;
// import javafx.scene.input.Dragboard;
// import javafx.scene.input.TransferMode;
// import libs.Player.Player;
// import libs.Card.Card;
// import libs.Card.Harvestable.HarvestableCard;

// public class FieldObserver extends GridPane {

//     public FieldObserver(Player player) {
//         Field field = player.getField();
//         int rows = field.getField().length;
//         int cols = field.getField()[0].length;

//         for (int i = 0; i < rows; i++) {
//             for (int j = 0; j < cols; j++) {
//                 final int row = i;
//                 final int col = j;

//                 ImageView cellView = new ImageView();
//                 cellView.setFitWidth(100);
//                 cellView.setFitHeight(100);
//                 if (field.getHarvestable(row, col) != null) {
//                     cellView.setImage(field.getHarvestable(row, col).getImage());
//                 }

//                 cellView.setOnDragOver(event -> {
//                     if (event.getGestureSource() != cellView && event.getDragboard().hasString()) {
//                         event.acceptTransferModes(TransferMode.MOVE);
//                     }
//                     event.consume();
//                 });

//                 cellView.setOnDragDropped(event -> {
//                     Dragboard db = event.getDragboard();
//                     boolean success = false;
//                     if (db.hasString()) {
//                         String cardName = db.getString();
//                         Card draggedCard = player.getActiveDeck().getCardByName(cardName);
//                         if (draggedCard instanceof HarvestableCard) {
//                             field.setHarvestable(row, col, (HarvestableCard) draggedCard);
//                             cellView.setImage(((HarvestableCard) draggedCard).getImage());
//                             player.getActiveDeck().removeCard(draggedCard);
//                             success = true;
//                         }
//                     }
//                     event.setDropCompleted(success);
//                     event.consume();
//                 });

//                 cellView.setOnDragDetected(event -> {
//                     HarvestableCard currentCard = field.getHarvestable(row, col);
//                     if (currentCard != null) {
//                         Dragboard db = cellView.startDragAndDrop(TransferMode.MOVE);
//                         ClipboardContent content = new ClipboardContent();
//                         content.putString(currentCard.getName());
//                         db.setContent(content);
//                         field.removeHarvestable(row, col);
//                         cellView.setImage(null);
//                         event.consume();
//                     }
//                 });

//                 this.add(cellView, j, i);
//             }
//         }
//     }
// }
