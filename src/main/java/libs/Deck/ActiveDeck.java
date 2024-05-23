package libs.Deck;

import libs.Card.Card;

public class ActiveDeck {
    private Card[] cards;
    private int cardCount;

    public ActiveDeck() {
        cards = new Card[6];
    }

    public void add(Card card) {
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] == null) {
                cards[i] = card;
                cardCount++;
                break;

            }
        }

    }

    public int getRemainingSlot() {
        return 6 - cardCount;
    }

    public boolean isFull() {
        return cardCount >= 6;
    }

    public void addCard(Card card, int index) {
        cards[index] = card;
        cardCount++;
    }

    public void removeCard(int index) {
        cards[index] = null;
        cardCount--;
    }

    public Card getCard(int index) {
        if (index < 0 || index >= cards.length) {
            return null;
        }
        return cards[index];
    }

    public Card[] getCards() {
        return cards;
    }
}
