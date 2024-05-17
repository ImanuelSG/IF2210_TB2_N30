package libs.GameWorld;

import java.util.ArrayList;

import libs.Player.Player;

// Karena singleton maka harus dibuat private constructor
public class GameWorld {
    private int turn;
    private ArrayList<Player> playerList;

    private static GameWorld instance;

    private GameWorld() {
        playerList = new ArrayList<>();
        turn = 0;

    }

    // Public static method to provide access to the instance
    public static synchronized GameWorld getInstance() {
        if (instance == null) {
            instance = new GameWorld();
        }
        return instance;
    }

    public void run() {
        while (turn < 20) {
            // Main game loop
            // Game logic
            // Player input
            // Update game state
            // Render game state
        }
        // Main game loop
    }

    public void nextTurn() {
        turn++;
    }

    public int getTurn() {
        return turn;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    // Other methods and fields of the GameWorld class
}
