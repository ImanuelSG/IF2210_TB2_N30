package libs.Player;
import libs.Deck.ActiveDeck;
import libs.Deck.Deck;
import libs.Field.Ladang;

public class Player {
    private String name;
    private int gulden;
    private Deck deck;
    private ActiveDeck activeDeck;
    private Ladang field;

    public Player(String name, int gulden) {
        this.name = name;
        this.gulden = gulden;
        this.deck = new Deck();
        this.activeDeck = new ActiveDeck();
        this.field = new Ladang(4, 5);
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
    }

    public void setActiveDeck(ActiveDeck activeDeck) {
        this.activeDeck = activeDeck;
    }

}
