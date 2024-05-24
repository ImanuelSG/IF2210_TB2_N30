package libs.Deck;

import java.util.ArrayList;
import java.util.Collections;
import libs.Card.Card;

public class Deck {
    protected ArrayList<Card> cards;

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public Card drawCard() {
        if (cards.size() > 0) {
            return cards.remove(cards.size() - 1);
        }
        return null;
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Card> shuffle(int quantity) {
        Collections.shuffle(cards);
        ArrayList<Card> shuffledCards = new ArrayList<>();
        for (int i = 0; i < quantity && i < cards.size(); i++) {
            shuffledCards.add(cards.get(i));
        }
        return shuffledCards;
    }

    public Card getCard(int index) {

        return cards.get(index);
    }

    public int getSize() {
        return cards.size();
    }
}
