package libs.GameWorld;

import java.util.ArrayList;

import controller.Observer;
import controller.Observerable;

import libs.Card.Harvestable.PlantCard;
import libs.Field.Ladang;
import libs.Player.Player;

// Karena singleton maka harus dibuat private constructor
public class GameWorld implements Observerable, SpecialObserverable {
    private int turn;
    private Player player1;
    private Player player2;
    private int state;
    private Player currentPlayer;

    private ArrayList<Observer> observers = new ArrayList<>();

    private ArrayList<SpecialObserver> specialobservers = new ArrayList<>();

    private static GameWorld instance;

    private GameWorld() {
        player1 = new Player("Player 1", 0, 40);
        player2 = new Player("Player 2", 0, 40);
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

        Ladang lp1 = player1.getField();
        Ladang lp2 = player2.getField();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (lp1.getHarvestable(i, j) != null) {
                    if (lp1.getHarvestable(i, j) instanceof PlantCard) {
                        PlantCard plant = (PlantCard) lp1.getHarvestable(i, j);
                        plant.addPlantAge();
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (lp2.getHarvestable(i, j) != null) {
                    if (lp2.getHarvestable(i, j) instanceof PlantCard) {
                        PlantCard plant = (PlantCard) lp2.getHarvestable(i, j);
                        plant.addPlantAge();
                    }
                }
            }
        }

        turn++;

        currentPlayer = (turn % 2 == 0) ? player2 : player1;

        System.out.println(currentPlayer.getName());
        movePhase(2);
        notifyObserver();
    }

    public void movePhase(int lastState) {

        if (lastState == 0) {
            state = 2;
        } else if (lastState == 1) {
            state = 2;
        } else {
            state = 0;
        }
        notifySpecial();
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

    @Override
    public void registerObserver(SpecialObserver observer) {
        specialobservers.add(observer);
    }

    @Override
    public void removeObserver(SpecialObserver observer) {
        specialobservers.remove(observer);
    }

    @Override
    public void notifySpecial() {
        for (SpecialObserver observer : specialobservers) {
            observer.update();
        }
    }
}