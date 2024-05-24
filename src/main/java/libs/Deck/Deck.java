package libs.Deck;

import java.util.ArrayList;
import java.util.Collections;

import controller.Observer;
import controller.Observerable;
import libs.Card.Card;
import libs.Card.CardFactory;

public class Deck implements Observerable {
    protected ArrayList<Card> cards;
    private ArrayList<Observer> observers;

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

    public void removeCards(ArrayList<Card> cards) {
        this.cards.removeAll(cards);
        notifyObserver();
    }

    public Card getCard(int index) {

        return cards.get(index);
    }

    public int getSize() {
        return cards.size();
    }

    public Deck(int size) {
        cards = new ArrayList<>();
        observers = new ArrayList<>();

        cards = CardFactory.seedDeck(size);

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
