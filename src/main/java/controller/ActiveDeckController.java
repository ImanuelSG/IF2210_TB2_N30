package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import libs.Card.Card;

import java.net.URL;
import java.util.ResourceBundle;

public class ActiveDeckController implements Initializable {
    @FXML
    private ImageView imageView1;
    @FXML
    private Label label1;
    @FXML
    private ImageView imageView2;
    @FXML
    private Label label2;
    @FXML
    private ImageView imageView3;
    @FXML
    private Label label3;
    @FXML
    private ImageView imageView4;
    @FXML
    private Label label4;
    @FXML
    private ImageView imageView5;
    @FXML
    private Label label5;
    @FXML
    private ImageView imageView6;
    @FXML
    private Label label6;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Example cards initialization
        setCard(imageView1, label1,
                new Card("Corn", new Image(getClass().getResourceAsStream("/img/tanaman/corn_seeds.png"))));
        setCard(imageView2, label2,
                new Card("Wheat", new Image(getClass().getResourceAsStream("/img/tanaman/pumpkin_seeds.png"))));
        setCard(imageView3, label3,
                new Card("Sheep", new Image(getClass().getResourceAsStream("/img/tanaman/strawberry_seeds.png"))));
        // Initialize other placeholders as needed
    }

    private void setCard(ImageView imageView, Label label, Card card) {
        imageView.setImage(card.getImage());
        label.setText(card.getName());
    }
}
