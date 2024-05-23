package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import libs.Card.CardFactory;
import libs.Card.Card;
import libs.Player.Player;
import libs.Toko.Toko;

public class TokoController {
    @FXML
    private GridPane stockGridPane;

    // Reference to the singleton instance of Toko
    private final Toko toko = Toko.getInstance();
    private final CardFactory cf = CardFactory.getInstance();

    Map<String, Integer> stocks;
    Map<String, Integer> prices;
    
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
        if (price > playerGulden) {
            throw new Exception("Not enough Gulden!");
        }

        boolean success = toko.removeProduct(name, quantity);

        if (success) {
            player.setGulden(playerGulden - price);
        }

        return success;
    }

    public boolean sellItem(Player player, Card card, int quantity) throws Exception {
        String name = card.getName();
        int price = prices.get(name);
        int playerGulden = player.getGulden();
        if (price > playerGulden) {
            throw new Exception("Not enough Gulden!");
        }

        boolean success = toko.removeProduct(name, quantity);

        if (success) {
            player.setGulden(playerGulden - price);
        }

        return success;
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
