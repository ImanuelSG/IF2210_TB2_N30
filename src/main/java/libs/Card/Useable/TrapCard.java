package libs.Card.Useable;

import javafx.scene.image.Image;
import libs.Card.Card;
import libs.Card.Harvestable.HarvestableCard;

public class TrapCard extends Card implements UseableOnSelf {

    public TrapCard(String name, Image image) {
        super(name, image);
    }

    @Override
    public void use(HarvestableCard target) {

        target.applyEffect("Trap");
    }
}
