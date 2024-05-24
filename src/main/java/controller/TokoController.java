package controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import libs.Card.CardFactory;
import libs.Toko.Toko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TokoController {

    @FXML
    private GridPane itemCards;

    private Map<String, Integer> stock;

    @FXML
    public void initialize() {
        Toko.getInstance().addProduct("SUSU",10);
        Toko.getInstance().addProduct("TELUR",10);
        Toko.getInstance().addProduct("STROBERI",10);
        Toko.getInstance().addProduct("JAGUNG",10);
        Toko.getInstance().addProduct("DAGING_KUDA",10);
        Toko.getInstance().addProduct("DAGING_DOMBA",10);
        Toko.getInstance().addProduct("DAGING_BERUANG",10);
        this.stock = new HashMap<String, Integer>();
        this.stock = Toko.getInstance().getStock();

        populateToko();
    }

    private BorderPane createTokoCard(String string, Integer quantity) {
        Map<String,ArrayList<String>> products = CardFactory.getInstance().getMapProduct();
        ArrayList<String> data = products.get(string);
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle(
                "-fx-background-color: #495749; -fx-background-radius: 10; -fx-padding: 10; -fx-min-width: 100; -fx-min-height: 150;"
        );

        HBox hbox = new HBox();
        hbox.setSpacing(10);


        // Kartu
        BorderPane cardPane = new BorderPane();
        cardPane.setStyle("-fx-background-color: #E2CC9F; -fx-background-radius: 10; -fx-padding: 10; -fx-min-width: 100; -fx-min-height: 150; -fx-border-color: #D49656;  -fx-border-width: 6px; -fx-border-radius: 7px;");

        VBox vBox = new VBox();
        vBox.setAlignment(javafx.geometry.Pos.CENTER);

        ImageView imageView = new ImageView();
        imageView.setImage(new Image(data.get(0)));
        imageView.setFitWidth(80);
        imageView.setFitHeight(100);
        VBox.setMargin(imageView, new Insets(0, 0, 10, 0));
        vBox.getChildren().add(imageView);

        Label label = new Label();
        label.setText(string);
        VBox.setMargin(label, new Insets(0, 0, 10, 0));
        vBox.getChildren().add(label);

        cardPane.setCenter(vBox);

        hbox.getChildren().add(cardPane);

        VBox textBox = new VBox();
        textBox.setAlignment(javafx.geometry.Pos.CENTER);

        Label hargaTitle = new Label();
        hargaTitle.setText("HARGA:");
        vBox.getChildren().add(hargaTitle);

        Label harga = new Label();
        harga.setText(data.get(1));
        vBox.getChildren().add(harga);

        Label stock_label = new Label();
        stock_label.setText("STOCK:");
        vBox.getChildren().add(stock_label);

        Label stock = new Label();
        stock.setText(quantity.toString());
        vBox.getChildren().add(stock);

        hbox.getChildren().add(textBox);

        borderPane.setCenter(hbox);
        return borderPane;
    }


    private void populateToko() {
        itemCards.getChildren().clear();

        int row = 0;
        int col = 0;

        for (String item : stock.keySet()) {
            Integer quantity = stock.get(item);
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
