package libs.Deck;

import java.util.ArrayList;

import libs.Card.Card;

public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<Card>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Card> shuffle(int quantity) {
        ArrayList<Card> shuffledCards = new ArrayList<Card>();
        for (int i = 0; i < quantity; i++) {
            int randomIndex = (int) (Math.random() * cards.size());
            shuffledCards.add(cards.get(randomIndex));
        }
        return shuffledCards;

    }

    

}
