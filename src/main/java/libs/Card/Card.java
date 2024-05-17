package libs.Card;

import javax.swing.ImageIcon;

public class Card {
    protected String name;
    protected ImageIcon image;

    public Card(String name, ImageIcon image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public ImageIcon getImage() {
        return image;
    }
}
