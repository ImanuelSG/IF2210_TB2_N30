package controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import libs.Card.CardFactory;
import libs.Card.Harvestable.AnimalCard;
import libs.Card.Harvestable.HarvestableCard;
import libs.Card.Products.ProductCard;
import libs.Card.Useable.UseableOnSelfCard;
import libs.Deck.ActiveDeck;
import libs.GameWorld.GameWorld;
import libs.Player.Player;
import libs.Toko.Toko;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TokoController {

    @FXML
    private GridPane itemCards;

    private Map<String, Integer> stock;

    @FXML
    private BorderPane sellBox;

    @FXML
    private ImageView sellBoxImage;

    @FXML
    private Label sellBoxText;



    @FXML
    public void initialize() {

        Toko.getInstance().addProduct("SUSU",10);
        Toko.getInstance().addProduct("TELUR",1);
        Toko.getInstance().addProduct("STROBERI",10);
        Toko.getInstance().addProduct("JAGUNG",10);
        Toko.getInstance().addProduct("DAGING_KUDA",10);
        Toko.getInstance().addProduct("DAGING_DOMBA",10);
        Toko.getInstance().addProduct("DAGING_BERUANG",10);
        this.stock = new HashMap<String, Integer>();
        this.stock = Toko.getInstance().getStock();

        populateToko();
        setupSellBoxDrag();
        initializeSellBox();
    }

    private BorderPane createTokoCard(String string, Integer quantity) {
        Map<String, ArrayList<String>> products = CardFactory.getInstance().getMapProduct();
        ArrayList<String> data = products.get(string);

        // Main BorderPane setup
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefWidth(400);
        borderPane.setPrefHeight(250);
        borderPane.setStyle(
                "-fx-background-color: #495749; -fx-background-radius: 10; -fx-padding: 10; -fx-min-height: 150;"
        );

        // Card Pane setup
        BorderPane cardPane = new BorderPane();
        cardPane.setPrefWidth(200);
        cardPane.setStyle(
                "-fx-background-color: #E2CC9F; -fx-background-radius: 10; -fx-padding: 10; -fx-min-height: 150; -fx-border-color: #D49656; -fx-border-width: 6px; -fx-border-radius: 7px;"
        );

        // Set the borderPane to be clickable
        borderPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> showConfirmation(borderPane, string, quantity));

        VBox vBox = new VBox();
        vBox.setAlignment(javafx.geometry.Pos.CENTER);

        ImageView imageView = new ImageView();
        imageView.setImage(new Image(data.get(0)));
        imageView.setFitWidth(100);
        imageView.setFitHeight(120);
        VBox.setMargin(imageView, new Insets(0, 0, 10, 0));
        vBox.getChildren().add(imageView);

        Label cardLabel = new Label();
        String[] parts = string.split("_");

        if (parts.length > 1) {
            // If there are multiple parts after splitting
            VBox labelsContainer = new VBox(); // Container for multiple labels
            labelsContainer.setSpacing(5); // Adjust spacing between labels if needed

            for (String part : parts) {
                Label partLabel = new Label(part);
                partLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
                partLabel.setWrapText(true);
                labelsContainer.getChildren().add(partLabel);
            }

            // Add the container of labels to the scene
            cardLabel.setGraphic(labelsContainer);
        } else {
            // If there's only one part after splitting
            cardLabel.setText(string.replace("_", "\n")); // Replace underscores with newlines
            cardLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
            cardLabel.setWrapText(true);
        }
        VBox.setMargin(cardLabel, new Insets(0, 0, 10, 0));
        vBox.getChildren().add(cardLabel);

        cardPane.setCenter(vBox);

        // HBox setup
        HBox hbox = new HBox();
        hbox.setSpacing(20);

        hbox.getChildren().add(cardPane);

        VBox textBox = new VBox();
        textBox.setAlignment(Pos.CENTER_LEFT);
        textBox.setSpacing(20);

        Label hargaTitle = new Label();
        hargaTitle.setText("HARGA:");
        hargaTitle.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;"); // Set larger font size
        hargaTitle.setWrapText(true);

        Label harga = new Label();
        harga.setText(data.get(1));
        harga.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        harga.setWrapText(true);

        Label stockLabel = new Label();
        stockLabel.setText("STOCK:");
        stockLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;"); // Set larger font size
        stockLabel.setWrapText(true);

        Label stock = new Label();
        stock.setText(quantity.toString());
        stock.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        stock.setWrapText(true);

        textBox.getChildren().addAll(hargaTitle, harga, stockLabel, stock);
        hbox.getChildren().add(textBox);

        borderPane.setCenter(hbox);
        return borderPane;
    }

    private  void refetch() {
        this.stock = Toko.getInstance().getStock();
    }

    private void populateToko() {

        itemCards.getChildren().clear();

        int row = 0;
        int col = 0;

        for (String item : stock.keySet()) {
            Integer quantity = stock.get(item);
            if (quantity > 0){

                BorderPane borderPane = createTokoCard(item, quantity);
                itemCards.add(borderPane, col, row);

                col++;
                if (col == 2) { // Assuming 2 items per row
                    col = 0;
                    row++;
                }
            }
        }
    }

    private void setupSellBoxDrag() {
        sellBox.setOnDragOver(event -> {
            if (event.getGestureSource() != sellBox && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        sellBox.setOnDragEntered(event -> {
            if (event.getGestureSource() != sellBox && event.getDragboard().hasString()) {
                sellBox.setStyle("-fx-background-color: #9FC47C; -fx-background-radius: 10; -fx-padding: 10; -fx-min-width: 100; -fx-min-height: 150; -fx-border-color: #495749;  -fx-border-width: 6px; -fx-border-radius: 7px;");
            }
        });

        sellBox.setOnDragExited(event -> {
            sellBox.setStyle("-fx-background-color: #E2CC9F; -fx-background-radius: 10; -fx-padding: 10; -fx-border-color: #D49656;  -fx-border-width: 6px; -fx-border-radius: 7px;");
        });

        sellBox.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                String[] data = db.getString().split("_");
                if (data.length == 3) {
                    String args = data[0];
                    String type = data[1];
                    String pos = data[2];
                    System.out.println(type);

                    ActiveDeck activeDeck = GameWorld.getInstance().getCurrentPlayer().getActiveDeck();
                    Player currPlayer = GameWorld.getInstance().getCurrentPlayer();
                    if (type.equals("Product")) {
                        Map<String, ArrayList<String>> products = CardFactory.getInstance().getMapProduct();
                        String item = args.replace(" ","_");
                        ArrayList<String> datas = products.get(item);
                        int price = Integer.parseInt(datas.get(1));
                        currPlayer.setGulden(price + currPlayer.getGulden());
                        activeDeck.removeCard(Integer.parseInt(pos));

                        Toko.getInstance().addProduct(item, 1);
                        refetch();
                        populateToko();
                        success = true;
                    }

                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    private void initializeSellBox() {
        sellBoxText.setText("JUAL PRODUK");
        sellBoxImage.setImage(new Image("/img/gui/jual.gif")); // Replace with your image path
        sellBoxImage.setFitWidth(100);
        sellBoxImage.setFitHeight(100);
    }

    private void showConfirmation(BorderPane borderPane, String item, Integer quantity) {
        Player currPlayer = GameWorld.getInstance().getCurrentPlayer();
        Map<String, ArrayList<String>> products = CardFactory.getInstance().getMapProduct();
        ArrayList<String> datas = products.get(item);


        Node originalContent = borderPane.getCenter();
        // Create a new BorderPane for the confirmation
        BorderPane confirmationPane = new BorderPane();


        confirmationPane.setPrefWidth(300);
        confirmationPane.setPrefHeight(200);
        confirmationPane.setStyle("-fx-background-color: #E2CC9F; -fx-background-radius: 10; -fx-padding: 10;");

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        Label confirmationLabel = new Label("Yakin Membeli?");
        confirmationLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        vbox.getChildren().add(confirmationLabel);

        Button confirmButton = new Button("BELI");

        if (currPlayer.getActiveDeck().isFull())
        {
            confirmButton.setDisable(true);
        }

        confirmButton.setOnAction(event -> {
            // Perform the confirmation action
            handleSell(item,currPlayer);
            refetch();
            populateToko();
        });

        Button cancelButton = new Button("BATAL");
        cancelButton.setOnAction(event -> {
            // Restore the original content
            borderPane.setCenter(originalContent);
        });

        confirmButton.getStyleClass().add("greenButton");
        cancelButton.getStyleClass().add("brownButton");

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(20);
        buttonBox.getChildren().addAll(confirmButton, cancelButton);

        vbox.getChildren().add(buttonBox);

        confirmationPane.setCenter(vbox);

        // Add the confirmation pane to the main borderPane
        BorderPane.setAlignment(confirmationPane, Pos.CENTER);
        borderPane.setCenter(confirmationPane);
    }


    public void handleSell(String item, Player currPlayer)
    {
        Map<String, ArrayList<String>> products = CardFactory.getInstance().getMapProduct();
        ArrayList<String> datas = products.get(item);
        int price = Integer.parseInt(datas.get(1));

        if (currPlayer.getGulden() >= price){
            ProductCard productCard = CardFactory.createProductCard(item);
            currPlayer.getActiveDeck().add(productCard);
            currPlayer.setGulden(currPlayer.getGulden() - price);
            Toko.getInstance().removeProduct(item,1);
        }
        else {
            System.out.println("uang ga cukup");
        }
        populateToko();


    }

}



