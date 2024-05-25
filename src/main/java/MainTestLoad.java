
// import javafx.application.Application;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.stage.Stage;
import java.io.File;
import java.util.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import libs.Card.CardFactory;
import libs.Deck.ActiveDeck;
import libs.GameWorld.GameWorld;
import libs.Toko.Toko;
import libs.Loader.Loader;
import libs.Player.*;
import libs.Deck.*;
import libs.Card.*;
import libs.Field.*;
import libs.Card.Harvestable.*;
import libs.FileManager.*;

public class MainTestLoad extends Application {

    public void start(Stage primaryStage) throws Exception {
        CardFactory.getInstance();
        Toko.getInstance();

        Parent root = FXMLLoader.load(getClass().getResource("view/StartView.fxml"));
        primaryStage.setTitle("Drag and Drop Example");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        // launch(args);
        CardFactory cf = CardFactory.getInstance();
        GameWorld g = GameWorld.getInstance();
        Toko t = Toko.getInstance();

        Scanner scanner = new Scanner(System.in);
        FileManager loader = FileManager.getInstance();

        while (true) {
            System.out.println();
            System.out.println(
                    "Enter the path to a JAR file to load a plugin, or a file to read (or type 'exit' to quit):");
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                break;
            }
            if ("clear".equalsIgnoreCase(input)) {
                g.setTurn(0);
                t.setItems(new HashMap<>());
                g.setPlayer1(new Player(".", 0, 0));
                g.setPlayer2(new Player(".", 0, 0));
                continue;
            }

            File file = new File(input);
            if (file.exists()) {
                if (input.endsWith(".jar")) {
                    try {
                        loader.loadJar(file);
                    } catch (Exception e) {
                        System.out.println("Failed to load JAR file: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    loader.loadFile(file, loader.getFileExtension(input));
                }
            } else {
                loader.saveFile(input, loader.getFileExtension(input));
            }

            System.out.println();
            System.out.println("Game state: ");
            System.out.println(g.getTurn());
            Map<String, Integer> stock = t.getStock();
            stock.forEach((key, value) -> System.out.println(key + " : " + value));

            System.out.println();
            System.out.println("Player 1: ");
            Player player1 = g.getPlayer1();
            System.out.println(player1.getGulden());

            Deck player1Deck = player1.getDeck();
            ActiveDeck player1ActiveDeck = player1.getActiveDeck();
            Ladang player1Ladang = player1.getField();

            System.out.println();
            System.out.println("Player 1 Deck: ");
            System.out.println(player1Deck.getSize());
            // for (int i = 0; i < player1Deck.getSize(); i++) {
            // Card activeDeckCard = player1Deck.getCard(i);
            // if (activeDeckCard != null) {
            // System.out.println(activeDeckCard.getName());
            // }
            // }

            System.out.println();
            System.out.println("Player 1 Active Deck: ");
            for (int i = 0; i < 6; i++) {
                Card activeDeckCard = player1ActiveDeck.getCard(i);
                if (activeDeckCard != null) {
                    System.out.println(i + 1);
                    System.out.println(activeDeckCard.getName());
                }
            }

            System.out.println();
            System.out.println("Player 1 Field: ");
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    HarvestableCard harvestableCard = player1Ladang.getHarvestable(i, j);
                    if (harvestableCard != null) {
                        System.out.println(i);
                        System.out.println(j);
                        System.out.println(harvestableCard.getName());
                        System.out.println(harvestableCard.getParameter());
                        Map<String, Integer> appliedEffects = harvestableCard.getAppliedEffect();
                        appliedEffects.forEach((key, value) -> System.out.println(key + " : " + value));
                    }
                }
            }

            System.out.println();
            System.out.println("Player 2: ");
            Player player2 = g.getPlayer2();
            System.out.println(player2.getGulden());

            Deck player2Deck = player2.getDeck();
            ActiveDeck player2ActiveDeck = player2.getActiveDeck();
            Ladang player2Ladang = player2.getField();

            System.out.println();
            System.out.println("Player 2 Deck: ");
            System.out.println(player2Deck.getSize());
            // for (int i = 0; i < player2Deck.getSize(); i++) {
            // Card activeDeckCard = player2Deck.getCard(i);
            // if (activeDeckCard != null) {
            // System.out.println(activeDeckCard.getName());
            // }
            // }

            System.out.println();
            System.out.println("Player 2 Active Deck: ");
            for (int i = 0; i < 6; i++) {
                Card activeDeckCard = player2ActiveDeck.getCard(i);
                if (activeDeckCard != null) {
                    System.out.println(i + 1);
                    System.out.println(activeDeckCard.getName());
                }
            }

            System.out.println();
            System.out.println("Player 2 Field: ");
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    HarvestableCard harvestableCard = player2Ladang.getHarvestable(i, j);
                    if (harvestableCard != null) {
                        System.out.println(i);
                        System.out.println(j);
                        System.out.println(harvestableCard.getName());
                        System.out.println(harvestableCard.getParameter());
                        Map<String, Integer> appliedEffects = harvestableCard.getAppliedEffect();
                        appliedEffects.forEach((key, value) -> System.out.println(key + " : " + value));
                    }
                }
            }
        }
    }
}
