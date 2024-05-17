package libs.Interfaces;

import javax.swing.ImageIcon;

public interface Harvestable {
    boolean isProtected();

    ImageIcon getImage();

    void applyEffect(String effect);

    boolean harvest();
}
