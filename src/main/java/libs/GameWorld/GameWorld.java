package libs.GameWorld;

import libs.Player.Player;

// Karena singleton maka harus dibuat private constructor
public class GameWorld {
    private int turn;
    private Player player1;
    private Player player2;
    private String state;

    private static GameWorld instance;

    private GameWorld() {
        player1 = new Player("Player 1", 0);
        player2 = new Player("Player 2", 0);
        turn = 0;
        state = "Shuffling";
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
        if (player1.getGulden() > player2.getGulden()) {
            System.out.println(player1.getName() + " wins!");
        } else {
            System.out.println(player2.getName() + " wins!");
        }
    }

    public void nextTurn() {
        turn++;

    }

    public int getTurn() {
        return turn;
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

    // Other methods and fields of the GameWorld class
}