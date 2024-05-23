package controllers.Deck;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import libs.Card.Card;
import libs.Deck.Deck;
import javafx.scene.image.Image;

public class DeckController {
    @FXML
    private ListView<Card> deckListView;

    private Deck deck;

    @FXML
    public void initialize() {
        deck = new Deck("Player 1");
        deckListView.setItems(FXCollections.observableArrayList(deck.getCards()));
    }

    @FXML
    private void handleAddCard() {
        // For demonstration, we create a card with a static image and name
        Card newCard = new Card("Card " + (deck.getCards().size() + 1), new Image("path/to/image.png"));
        deck.addCard(newCard);
        updateDeckListView();
    }

    @FXML
    private void handleRemoveCard() {
        Card selectedCard = deckListView.getSelectionModel().getSelectedItem();
        if (selectedCard != null) {
            deck.removeCard(selectedCard);
            updateDeckListView();
        }
    }

    private void updateDeckListView() {
        deckListView.setItems(FXCollections.observableArrayList(deck.getCards()));
    }
}
