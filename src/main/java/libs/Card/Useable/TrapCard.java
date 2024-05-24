package libs.Card.Useable;

import javafx.scene.image.Image;
import libs.Card.Harvestable.HarvestableCard;

public class TrapCard extends UseableOnSelfCard {

    public TrapCard(String name, Image image) {
        super(name, image);
    }

    @Override
    public void use(HarvestableCard target) {

        target.applyEffect("Trap");
    }
}
