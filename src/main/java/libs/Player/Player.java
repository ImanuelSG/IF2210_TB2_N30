package libs.Player;

import java.util.ArrayList;

import libs.Card.Card;
import libs.Deck.ActiveDeck;
import libs.Deck.Deck;
import libs.Field.Field;

public class Player {
    private String name;
    private int gulden;
    private Deck deck;
    private ActiveDeck activeDeck;
    private Field field;

    public Player(String name, int gulden) {
        this.name = name;
        this.gulden = gulden;
        this.deck = new Deck(name);
        this.activeDeck = new ActiveDeck(name);
        this.field = new Field(4, 5);
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

    public Field getField() {
        return field;
    }

    public void setGulden(int gulden) {
        this.gulden = gulden;
    }

    public void setActiveDeck(ActiveDeck activeDeck) {
        this.activeDeck = activeDeck;
    }

    public ArrayList<Card> drawCard() {
        return this.deck.shuffle(6 - this.activeDeck.getCards().size());
    }

}
