package libs.Card;

import javafx.scene.image.Image;

public class Card {
    protected String name;
    protected Image image;

    public Card() {

    }

    public Card(String name, Image image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }
}
