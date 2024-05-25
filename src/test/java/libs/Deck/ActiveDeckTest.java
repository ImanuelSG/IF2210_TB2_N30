package libs.Deck;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Observer;
import javafx.scene.image.Image;
import libs.Card.Card;

public class ActiveDeckTest {

    private ActiveDeck deck;
    private Card card;

    @BeforeEach
    public void setUp() {
        deck = new ActiveDeck();
        Image image = new Image("/img/item/accelerate.png");
        card = new Card("ACCELERATE", image); // Assuming a simple Card class with a no-arg constructor
    }

    @Test
    public void testAddCard() {
        deck.add(card);
        assertEquals(1, deck.getCardCount());
        assertEquals(card, deck.getCard(0));
    }

    @Test
    public void testRemoveCard() {
        deck.add(card);
        deck.removeCard(0);
        assertEquals(0, deck.getCardCount());
        assertNull(deck.getCard(0));
    }

    @Test
    public void testGetRemainingSlot() {
        deck.add(card);
        assertEquals(5, deck.getRemainingSlot());
    }

    @Test
    public void testIsFull() {
        for (int i = 0; i < 6; i++) {
            Image image = new Image("/img/item/accelerate.png");
            card = new Card("ACCELERATE", image); // Assuming a simple Card class with a no-arg constructor
        }
        assertTrue(deck.isFull());
    }

    @Test
    public void testAddCardAtIndex() {
        deck.addCard(card, 2);
        assertEquals(card, deck.getCard(2));
    }

    @Test
    public void testAddCardAtLocation() {
        deck.addCard(card, "C01");
        assertEquals(card, deck.getCard(2));
    }

    @Test
    public void testAddObserver() {
        Observer observer = new Observer() {
            @Override
            public void updateView() {
                // Empty implementation for test
            }
        };
        deck.addObserver(observer);
        // No direct way to assert observer notification without a mock framework
        deck.notifyObserver();
    }

    @Test
    public void testRemoveObserver() {
        Observer observer = new Observer() {
            @Override
            public void updateView() {
                // Empty implementation for test
            }
        };
        deck.addObserver(observer);
        deck.removeObserver(observer);
        deck.notifyObserver();
    }
}
