package libs.Card.Harvestable;


import javafx.scene.image.Image;

public interface Harvestable {
    boolean isProtected();

    Image getImage();

    void applyEffect(String effect);

    String harvest();
}
