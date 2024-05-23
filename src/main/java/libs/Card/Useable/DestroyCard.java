package libs.Card.Useable;

import libs.Card.Card;
import libs.Card.Harvestable.HarvestableCard;
import libs.Interfaces.UseableOnHarvestable;
import javafx.scene.image.Image;

public class DestroyCard extends Card implements UseableOnHarvestable {
    public DestroyCard(String name, Image image) {
        super(name, image);
    }

    @Override
    public void use(HarvestableCard target) {
        
    }

}
