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
        if (playerList.get(0).getGulden() > playerList.get(1).getGulden()) {
            System.out.println(playerList.get(0).getName() + " wins!");
        } else {
            System.out.println(playerList.get(1).getName() + " wins!");
        }
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

    public void setTurn(int turn) {
        this.turn = turn;
    }

    // Other methods and fields of the GameWorld class
}
