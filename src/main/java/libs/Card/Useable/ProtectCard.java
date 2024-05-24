package libs.Card.Useable;

import libs.Card.Harvestable.HarvestableCard;
import libs.Field.Ladang;
import libs.GameWorld.GameWorld;

public class ProtectCard extends UseableOnSelfCard {
    public ProtectCard(String name, javafx.scene.image.Image image) {
        super(name, image);
    }

    @Override
    public void use(HarvestableCard target) {
        Ladang field = GameWorld.getInstance().getCurrentPlayer().getField();

        

        target.applyEffect("Protect");

    }

}
