package libs.GameWorld;

import java.util.ArrayList;
import java.util.Random;

import controller.Observer;
import controller.Observerable;

import libs.Card.Harvestable.PlantCard;
import libs.Field.Ladang;
import libs.Player.Player;

// Karena singleton maka harus dibuat private constructor
public class GameWorld implements Observerable, SpecialObserverable, BearAttackNotifier {
    private int turn;
    private Player player1;
    private Player player2;
    private int state;
    private Player currentPlayer;
    private Random randomizer;
    // private AudioPlayer audioPlayer =
    // AudioPlayer.getInstance("/audio/backsound.mp3", "/audio/attack.mp3");

    // For Bear Attack
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;
    private int duration;

    private ArrayList<BearAttackListener> listener = new ArrayList<>();

    private ArrayList<Observer> observers = new ArrayList<>();

    private ArrayList<SpecialObserver> specialobservers = new ArrayList<>();

    private static GameWorld instance;

    private GameWorld() {
        player1 = new Player("Player 1", 0, 40);
        player2 = new Player("Player 2", 0, 40);
        turn = 1;
        state = 0;
        currentPlayer = player1;
        randomizer = new Random();
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

        movePhase(2);
        notifyObserver();
    }

    public void movePhase(int lastState) {

        if (lastState == 0) {

            double probs = randomizer.nextDouble();

            if (probs <= 0.30) {
                state = 1;
                notifySpecial();
                // audioPlayer.play();

                this.duration = randomizer.nextInt(60 - 30 + 1) + 30;
                boolean found = false;
                int result;
                int startX;
                int startY;
                int endX;
                int endY;
                do {
                    startX = randomizer.nextInt(5);
                    startY = randomizer.nextInt(4);

                    endX = randomizer.nextInt(5);
                    endY = randomizer.nextInt(4);

                    result = (Math.abs(startX - endX) + 1) * (Math.abs(startY - endY) + 1);
                    if (result <= 6 && result >= 0) {
                        found = true;
                    }
                } while (!found);

                minX = Math.min(startX, endX);
                minY = Math.min(startY, endY);
                maxX = Math.max(startX, endX);
                maxY = Math.max(startY, endY);

                notifyListener(minY, maxY, minX, maxX, 10);
            } else {
                state = 2;
                notifySpecial();
            }
        } else if (lastState == 1) {
            this.endBearNotify();
            state = 2;
            notifySpecial();
            // audioPlayer.play();
        } else {
            state = 0;
            notifySpecial();
            // audioPlayer.play();
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

    public boolean isBearAttack() {
        return this.state == 1;
    }

    public boolean isAttacked(int row, int col) {
        return row >= minY && row <= maxY && col >= minX && col <= maxX;
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

    @Override
    public void addListener(BearAttackListener listener) {
        this.listener.add(listener);
    }

    @Override
    public void notifyListener(int startrow, int endrow, int startcol, int endcol, int duration) {
        for (BearAttackListener ls : listener) {

            ls.setupBearAttack(startrow, endrow, startcol, endcol, duration);
        }
    }

    private void endBearNotify() {
        for (BearAttackListener ls : listener) {
            ls.endBearAttack();
        }
    }
}