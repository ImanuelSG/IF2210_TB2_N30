package libs.Player;

import libs.Deck.Deck;

public class Player {
    private String name;
    private int gulden;
    private Deck deck;
    private Deck activeDeck;

    public Player(String name, int gulden) {
        this.name = name;
        this.gulden = gulden;
        this.deck = new Deck();
        this.activeDeck = new Deck();
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

    public Deck getActiveDeck() {
        return activeDeck;
    }

    public void setGulden(int gulden) {
        this.gulden = gulden;
    }

    public void setActiveDeck(Deck activeDeck) {
        this.activeDeck = activeDeck;
    }

}
