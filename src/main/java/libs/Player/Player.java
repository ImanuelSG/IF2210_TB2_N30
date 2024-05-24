package libs.Player;

import controller.Observer;
import controller.Observerable;
import libs.Deck.ActiveDeck;
import libs.Deck.Deck;
import libs.Field.Ladang;

import java.util.ArrayList;

public class Player implements Observerable {
    private String name;
    private int gulden;
    private Deck deck;
    private ActiveDeck activeDeck;
    private Ladang field;
    private ArrayList<Observer> observers;

    public Player(String name, int gulden, int decksize) {
        this.name = name;
        this.gulden = gulden;
        this.deck = new Deck(40);
        this.activeDeck = new ActiveDeck();
        this.field = new Ladang();
        this.observers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getGulden() {
        return gulden;
    }

    public Deck getDeck() {
        return deck;
    }

    public ActiveDeck getActiveDeck() {
        return activeDeck;
    }

    public Ladang getField() {
        return field;
    }

    public void setGulden(int gulden) {

        this.gulden = gulden;
        notifyObserver();
    }

    public void setActiveDeck(ActiveDeck activeDeck) {
        this.activeDeck = activeDeck;
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
