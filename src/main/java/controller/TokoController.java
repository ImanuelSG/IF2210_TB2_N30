package controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import libs.Toko.Toko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TokoController {

    @FXML
    private GridPane itemCards;

    private Map<String, Integer> stock;

    private Toko toko;

    @FXML
    public void initialize() {
        this.stock = new HashMap<String, Integer>();
        this.stock = Toko.getInstance().getStock();
        this.toko = Toko.getInstance();
    }

//    private BorderPane createTokoCard(String string) {
//
//        String product
//        BorderPane borderPane = new BorderPane();
//        borderPane.setStyle(
//                "-fx-background-color: #495749; -fx-background-radius: 10; -fx-padding: 10; -fx-min-width: 100; -fx-min-height: 150;"
//        );
//
//        HBox hbox = new HBox();
//        hbox.setSpacing(10);
//
//        BorderPane cardPane = new BorderPane();
//        borderPane.setStyle("-fx-background-color: #E2CC9F; -fx-background-radius: 10; -fx-padding: 10; -fx-min-width: 100; -fx-min-height: 150; -fx-border-color: #D49656;  -fx-border-width: 6px; -fx-border-radius: 7px;");
//
//        VBox vBox = new VBox();
//        vBox.setAlignment(javafx.geometry.Pos.CENTER);
//
//        ImageView imageView = new ImageView();
//        imageView.setFitWidth(80);
//        imageView.setFitHeight(100);
//        VBox.setMargin(imageView, new Insets(0, 0, 10, 0));
//        vBox.getChildren().add(imageView);
//
//        Label label = new Label();
//        VBox.setMargin(label, new Insets(0, 0, 10, 0));
//        vBox.getChildren().add(label);
//
//        borderPane.setCenter(vBox);
//
//    }




}
