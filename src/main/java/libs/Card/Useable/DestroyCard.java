package libs.Card.Useable;

import libs.Card.Card;
import libs.Card.Harvestable.HarvestableCard;
import libs.Interfaces.UseableOnHarvestable;
import libs.Player.Player;

public class DestroyCard extends Card implements UseableOnHarvestable {
    public DestroyCard(String name, javafx.scene.image.Image image) {
        super(name, image);
    }

    @Override
    public void use(HarvestableCard target) {

    }

}
