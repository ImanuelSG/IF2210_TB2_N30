package libs.Deck;

import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import libs.Card.Card;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ActiveDeck extends Deck {

    public ActiveDeck(String name) {
        super(name);
    }

    public HBox createActiveDeckView() {

        System.out.println("Creating active deck view");
        HBox deckView = new HBox();
        for (Card card : this.getCards()) {

            Text cardName = new Text(card.getName());

            // Creating the image view
            ImageView cardView = new ImageView();
            // Setting image to the image view
            cardView.setImage(card.getImage());
            System.out.println(card.getImage());
            // Setting the image view parameters
            cardView.setX(10);
            cardView.setY(10);
            cardView.setFitWidth(575);
            cardView.setPreserveRatio(true);

            cardView.setOnDragDetected(event -> {
                Dragboard db = cardView.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(card.getName());
                db.setContent(content);
                event.consume();
            });

            deckView.getChildren().add(cardView);
            deckView.getChildren().add(cardName);
        }
        return deckView;
    }

    public void updateDeckView(HBox deckView) {
        deckView.getChildren().clear();
        for (Card card : this.getCards()) {
            ImageView cardView = new ImageView(card.getImage());
            cardView.setFitWidth(100);
            cardView.setFitHeight(100);

            cardView.setOnDragDetected(event -> {
                Dragboard db = cardView.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(card.getName());
                db.setContent(content);
                event.consume();
            });

            deckView.getChildren().add(cardView);
        }
    }

    public Card getCardByName(String name) {
        for (Card card : cards) {
            if (card.getName().equals(name)) {
                return card;
            }
        }
        return null;
    }

}
