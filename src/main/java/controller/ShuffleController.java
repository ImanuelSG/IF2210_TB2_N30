package controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import libs.Card.Card;
import libs.GameWorld.GameWorld;
import libs.Player.Player;

import java.util.ArrayList;

public class ShuffleController {
    @FXML
    private GridPane shuffleCards;

    @FXML
    private VBox shufflePopUp;

    @FXML
    private Button reshuffleButton;

    @FXML
    private Button accButton;

    @FXML
    private void handleReshuffleButton() {
        setCardList();
    }
    @FXML
    private void handleAccButton() {

    }




    private ArrayList<Card> shuffledCard;

    private void setCardList() {
        Player currPlayer = GameWorld.getInstance().getCurrentPlayer();
        int slotActiveDeck = currPlayer.getActiveDeck().getRemainingSlot();
        this.shuffledCard = currPlayer.getDeck().shuffle(slotActiveDeck);
        if (shuffledCard.size() > 0) {

            System.out.println(shuffleCards);
        } else {
            System.out.println("kosong");
        }
    }

    @FXML
    public void initialize() {
        System.out.println("initialize");
        // Initialize your components and add event listeners if needed
        this.shuffledCard = new ArrayList<Card>();
        setCardList();
        populateShuffleCards();
    }

    private BorderPane createGridCell(Card card) {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle(
                "-fx-background-color: #E2CC9F; -fx-background-radius: 10; -fx-padding: 10; -fx-min-width: 150; -fx-min-height: 200; -fx-border-color: #D49656; -fx-border-width: 6px; -fx-border-radius: 7px;");

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView();
        imageView.setImage(card.getImage());
        imageView.setFitWidth(120); // Larger width
        imageView.setFitHeight(150); // Larger height
        VBox.setMargin(imageView, new Insets(0, 0, 10, 0));
        vBox.getChildren().add(imageView);

        Label label = new Label(card.getName());
        VBox.setMargin(label, new Insets(0, 0, 10, 0));
        vBox.getChildren().add(label);

        borderPane.setCenter(vBox);

        return borderPane;
    }

    public void populateShuffleCards() {
        shuffleCards.getChildren().clear();

        int row = 0;
        int col = 0;
        for (Card card : this.shuffledCard) {
            BorderPane cardPane = createGridCell(card);
            shuffleCards.add(cardPane, col, row);
            col++;
            if (col == 2) { // Assuming 3 cards per row
                col = 0;
                row++;
            }
        }
    }
}

