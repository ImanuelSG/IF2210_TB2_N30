package libs.GameWorld;

import java.util.ArrayList;

import controller.Observer;
import controller.Observerable;
import libs.Card.CardFactory;
import libs.Player.Player;

// Karena singleton maka harus dibuat private constructor
public class GameWorld implements Observerable {
    private int turn;
    private Player player1;
    private Player player2;
    private int state;
    private Player currentPlayer;

    private ArrayList<Observer> observers = new ArrayList<>();

    private static GameWorld instance;

    private GameWorld() {
        player1 = new Player("Player 1", 0, 40);
        player2 = new Player("Player 2", 0, 40);
        player1.getActiveDeck().add(CardFactory.createHarvestableCard("AYAM"));
        player1.getActiveDeck().add(CardFactory.createProductCard("DAGING BERUANG"));
        player1.getActiveDeck().add(CardFactory.createHarvestableCard("BIJI_JAGUNG"));
        player1.getActiveDeck().add(CardFactory.createProductCard("DAGING BERUANG"));
        player1.getActiveDeck().add(CardFactory.createItemCard("ACCELERATE"));

        player2.getActiveDeck().add(CardFactory.createHarvestableCard("HIU DARAT"));
        player2.getActiveDeck().add(CardFactory.createProductCard("DAGING BERUANG"));
        player2.getActiveDeck().add(CardFactory.createHarvestableCard("BERUANG"));
        player2.getActiveDeck().add(CardFactory.createHarvestableCard("HIU DARAT"));
        player2.getActiveDeck().add(CardFactory.createProductCard("DAGING BERUANG"));
        player2.getActiveDeck().add(CardFactory.createHarvestableCard("BERUANG"));

        turn = 1;
        state = 0;
        currentPlayer = player1;
    }

    // Public static method to provide access to the instance
    public static synchronized GameWorld getInstance() {
        if (instance == null) {
            instance = new GameWorld();
        }
        return instance;
    }

    public void nextTurn() {
        turn++;
        currentPlayer = (turn % 2 == 0) ? player1 : player2;
        System.out.println("Turn " + turn + " " + currentPlayer.getName() + " " + state);
        notifyObserver();
    }

    public void movePhase() {
        if (state == 0) {

        } else {
            state = 0;
            nextTurn();
        }
    }

    public int getTurn() {
        return turn;
    }

    public Player getEnemy() {
        return (currentPlayer == player1) ? player2 : player1;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getState() {
        return state;

    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    // Other methods and fields of the GameWorld class

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