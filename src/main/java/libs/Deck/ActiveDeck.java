package libs.Deck;

import java.util.ArrayList;

import controller.Observer;
import controller.Observerable;
import libs.Card.Card;

public class ActiveDeck implements Observerable {
    private Card[] cards;
    private int cardCount;
    private ArrayList<Observer> observers;

    public ActiveDeck() {
        cards = new Card[6];
        observers = new ArrayList<>();
    }

    public void add(Card card) {
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] == null) {
                cards[i] = card;
                cardCount++;
                break;

            }
        }
        notifyObserver();
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
        notifyObserver();
    }

    public void removeCard(int index) {
        cards[index] = null;
        cardCount--;
        notifyObserver();
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

    public int getCardCount() {
        return cardCount;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : observers) {
            observer.updateView();
        }
    }
}
