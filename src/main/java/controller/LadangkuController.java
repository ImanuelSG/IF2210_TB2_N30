package controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import libs.Card.CardFactory;
import libs.Card.Harvestable.AnimalCard;
import libs.Card.Harvestable.HarvestableCard;
import libs.Card.Harvestable.PlantCard;
import libs.Card.Useable.UseableOnSelfCard;
import libs.Deck.ActiveDeck;
import libs.Field.Ladang;
import libs.GameWorld.GameWorld;

public class LadangkuController implements Initializable, Observer {

    @FXML
    private GridPane gridPane;


    @FXML
    private Ladang ladang;

    @FXML
    private VBox labelPopUp;

    @FXML
    private ImageView sellBoxImage;

    @FXML
    private Text titleLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private Label descLabel;

    @FXML
    private VBox attrLabel;

    @FXML
    private Button backButton;

    @FXML
    private Button panenButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameWorld main = GameWorld.getInstance();
        main.addObserver(this);
        main.getCurrentPlayer().getField().addObserver(this);
        main.getEnemy().getField().addObserver(this);

        ladang = GameWorld.getInstance().getCurrentPlayer().getField();

        labelPopUp.setVisible(false);
        populateGrid();
        updateView();
    }

    private void populateGrid() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                BorderPane borderPane = createGridCell();
                gridPane.add(borderPane, j, i);
            }
        }
    }

    private BorderPane createGridCell() {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle(
                "-fx-background-color: #E2CC9F; -fx-background-radius: 10; -fx-padding: 10; -fx-min-width: 100; -fx-min-height: 150; -fx-border-color: #D49656;  -fx-border-width: 6px; -fx-border-radius: 7px;");

        VBox vBox = new VBox();
        vBox.setAlignment(javafx.geometry.Pos.CENTER);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(80);
        imageView.setFitHeight(100);
        VBox.setMargin(imageView, new Insets(0, 0, 10, 0));
        vBox.getChildren().add(imageView);

        Label label = new Label();
        VBox.setMargin(label, new Insets(0, 0, 10, 0));
        vBox.getChildren().add(label);

        borderPane.setCenter(vBox);

        borderPane.setOnDragOver(event -> handleDragOver(event, borderPane));
        borderPane.setOnDragExited(event -> handleDragExited(event, borderPane));
        borderPane.setOnDragDropped(event -> handleDragDropped(event, borderPane));
        borderPane.setOnMouseClicked(event -> handleCellClick(borderPane));

        return borderPane;
    }

    private void handleDragOver(DragEvent event, BorderPane borderPane) {
        if (event.getGestureSource() != gridPane && event.getDragboard().hasString()) {
            borderPane.setStyle(
                    "-fx-background-color: #9FC47C; -fx-background-radius: 10; -fx-padding: 10; -fx-min-width: 100; -fx-min-height: 150; -fx-border-color: #495749;  -fx-border-width: 6px; -fx-border-radius: 7px;");
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    private void handleDragExited(DragEvent event, BorderPane borderPane) {
        borderPane.setStyle(
                "-fx-background-color: #E2CC9F; -fx-background-radius: 10; -fx-padding: 10; -fx-min-width: 100; -fx-min-height: 150; -fx-border-color: #D49656;  -fx-border-width: 6px; -fx-border-radius: 7px;");
        event.consume();
    }

    private void handleDragDropped(DragEvent event, BorderPane borderPane) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            String[] data = db.getString().split("_");
            if (data.length == 3) {
                String args = data[0];
                String type = data[1];
                String pos = data[2];
                int columnIndex = GridPane.getColumnIndex(borderPane);
                int rowIndex = GridPane.getRowIndex(borderPane);
                ActiveDeck activeDeck = GameWorld.getInstance().getCurrentPlayer().getActiveDeck();

                if (ladang.getHarvestable(rowIndex, columnIndex) == null && type.equals("Harvestable")) {
                    VBox centerBox = (VBox) borderPane.getCenter();
                    ImageView imageView = (ImageView) centerBox.getChildren().get(0);
                    Label label = (Label) centerBox.getChildren().get(1);
                    HarvestableCard card = CardFactory.createHarvestableCard(args);
                    imageView.setImage(card.getImage());
                    label.setText(card.getName());
                    ladang.setHarvestable(rowIndex, columnIndex, card);
                    activeDeck.removeCard(Integer.parseInt(pos));
                    success = true;
                    // Handle product Drop
                } else if (type.equals("Product")
                        && ladang.getHarvestable(rowIndex, columnIndex) instanceof AnimalCard) {
                    AnimalCard animal = (AnimalCard) ladang.getHarvestable(rowIndex, columnIndex);
                    try {
                        animal.feed(CardFactory.createProductCard(args));
                        activeDeck.removeCard(Integer.parseInt(pos));

                        success = true;
                    } catch (Exception e) {
                        System.out.println(e);

                    }
                    // Handle Item Drop
                } else if (type.equals("UseableSelf") && ladang.getHarvestable(rowIndex, columnIndex) != null) {
                    HarvestableCard card = ladang.getHarvestable(rowIndex, columnIndex);

                    System.out.println(args);
                    UseableOnSelfCard usecard = (UseableOnSelfCard) CardFactory.createItemCard(args);
                    usecard.use(card);
                    activeDeck.removeCard(Integer.parseInt(pos));
                    success = true;

                }
                updateView();
            }
        }

        event.setDropCompleted(success);
        event.consume();
    }

    private void handleCellClick(BorderPane borderPane) {
        int columnIndex = GridPane.getColumnIndex(borderPane);
        int rowIndex = GridPane.getRowIndex(borderPane);
        HarvestableCard card = ladang.getHarvestable(rowIndex, columnIndex);

        if (card != null) {
            attrLabel.getChildren().clear();
            labelPopUp.setVisible(true);

            titleLabel.setText(card.getName());
            ;

            // Determine the parameter text based on the card type
            String parameterText = "";
            if (card instanceof PlantCard) {
                parameterText = "Age: " + card.getParameter();
            } else if (card instanceof AnimalCard) {
                parameterText = "Weight: " + ((AnimalCard) card).getParameter();
            }

            descLabel.setText(parameterText);

            Map<String, Integer> attributes = card.getAppliedEffect();
            System.out.println(attributes);
            if (attributes != null) {
                for (Map.Entry<String, Integer> entry : attributes.entrySet()) {
                    System.out.println("Ayam");
                    Label attributeLabel = new Label(entry.getKey() + ": " + entry.getValue());
                    attrLabel.getChildren().add(attributeLabel);
                }
            }

            Image img = card.getImage();
            imageView.setImage(img);

            this.panenButton.setDisable(!card.isHarvestable()
                    || GameWorld.getInstance().getCurrentPlayer().getActiveDeck().getRemainingSlot() == 0);
            this.panenButton.setOnAction(e -> handlePanenButton(card));

        }
    }

    // Method to hide the labelPopUp
    public void hideLabelPopUp() {
        labelPopUp.setVisible(false);
    }




    // Event handler for backButton
    @FXML
    private void handleBackButton() {
        hideLabelPopUp();
    }

    // Event handler for panenButton
    @FXML
    private void handlePanenButton(HarvestableCard card) {
        String product = card.harvest();
        CardFactory.createProductCard(product);
        GameWorld.getInstance().getCurrentPlayer().getActiveDeck().add(CardFactory.createProductCard(product));
        ladang.removeHarvestable(card);

        hideLabelPopUp();
    }

    @Override
    public void updateView() {
        ladang = GameWorld.getInstance().getCurrentPlayer().getField();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                BorderPane borderPane = (BorderPane) gridPane.getChildren().get(i * 5 + j);
                HarvestableCard card = ladang.getHarvestable(i, j);
                ImageView imageView = (ImageView) ((VBox) borderPane.getCenter()).getChildren().get(0);
                Label label = (Label) ((VBox) borderPane.getCenter()).getChildren().get(1);
                if (card != null) {
                    imageView.setImage(card.getImage());
                    label.setText(card.getName());
                } else {
                    imageView.setImage(null);
                    label.setText("");
                }
            }
        }

    }

}
