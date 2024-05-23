package libs.GameWorld;

import java.util.ArrayList;

import controller.Observer;
import controller.Observerable;
import libs.Player.Player;

// Karena singleton maka harus dibuat private constructor
public class GameWorld implements Observerable {
    private int turn;
    private Player player1;
    private Player player2;
    private String state;
    private Player currentPlayer;

    private ArrayList<Observer> observers = new ArrayList<>();

    private static GameWorld instance;

    private GameWorld() {
        player1 = new Player("Player 1", 0);
        player2 = new Player("Player 2", 0);
        turn = 0;
        state = "Shuffling";
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

    public String getState() {
        return state;

    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
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