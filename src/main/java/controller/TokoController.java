package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import libs.Card.CardFactory;
import libs.Card.Card;
import libs.Player.Player;
import libs.Toko.Toko;
import libs.Deck.ActiveDeck;

public class TokoController {
    @FXML
    private GridPane stockGridPane;

    // Reference to the singleton instance of Toko
    private final Toko toko = Toko.getInstance();

    Map<String, Integer> stocks;
    Map<String, Integer> prices;

    public TokoController(){};
    
    public void initializeStocks() {
        stocks = toko.getStock();
    }

    public void initializePrices() {
        // Get the MapProduct from the CardFactory
        Map<String, ArrayList<String>> mapProduct = CardFactory.MapProduct;

        // Initialize the prices map
        prices = new HashMap<>();

        // Iterate through each entry in the MapProduct
        for (Map.Entry<String, ArrayList<String>> entry : mapProduct.entrySet()) {
            String productName = entry.getKey();
            ArrayList<String> productInfo = entry.getValue();

            int price = Integer.parseInt(productInfo.get(1));

            // Add the product name and price to the prices map
            prices.put(productName, price);
        }
    }

    public boolean buyItem(Player player, Card card, int quantity) throws Exception {
        assert(quantity > 0);
        String name = card.getName();
        int price = prices.get(name);
        int playerGulden = player.getGulden();
        if (price * quantity > playerGulden) {
            throw new Exception("Not enough Gulden!");
        }
        
        ActiveDeck activeDeck = player.getActiveDeck();
        if ((activeDeck.getCards()).size() + quantity > 6) {
            throw new Exception("Not enough active deck slot!");
        }

        for (int i = 0; i < quantity; i++) {
            Card tempCard = new Card(card.getName(), card.getImage());
            activeDeck.addCard(tempCard);
        }

        boolean success = toko.removeProduct(name, quantity);

        if (success) {
            player.setGulden(playerGulden - price * quantity);
            player.setActiveDeck(activeDeck);
        }

        initializeStocks();
        return success;
    }

    public void sellItem(Player player, Card card, int quantity) throws Exception {
        ActiveDeck activeDeck = player.getActiveDeck();
        if (activeDeck.getCards().size() < quantity) {
            throw new Exception("Not enough item to sell!");
        }

        for (int i = 0; i < quantity; i++) {
            activeDeck.removeCard(card);
        }

        String name = card.getName();
        int price = prices.get(name);

        toko.addProduct(name, quantity);
        player.setGulden(player.getGulden() + price * quantity);

        initializeStocks();
    }

    // @FXML
    // public void initialize() {
    //     // Get the stock from Toko
    //     Map<String, Integer> stock = toko.getStock();

    //     // Initialize row index
    //     int row = 0;

    //     for (Map.Entry<String, Integer> entry : stock.entrySet()) {
    //         String productName = entry.getKey();
    //         int quantity = entry.getValue();

    //         // Create a Label for the product name and quantity
    //         Label nameLabel = new Label(productName);
    //         Label quantityLabel = new Label("Quantity: " + quantity);

    //         // Add the labels to the corresponding cells in the GridPane
    //         stockGridPane.addRow(row++, nameLabel, quantityLabel);
    //     }
    // }

    // @FXML
    // private void handleCloseAction(ActionEvent event) {
    //     // Get the stage containing the scene
    //     Stage stage = (Stage) stockGridPane.getScene().getWindow();
    //     // Close the stage
    //     stage.close();
    // }
};
