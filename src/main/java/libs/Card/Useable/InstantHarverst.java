package libs.Card.Useable;

import javafx.scene.image.Image;
import libs.Card.Card;
import libs.Card.Harvestable.HarvestableCard;
import libs.Interfaces.UseableOnHarvestable;

public class InstantHarverst extends Card implements UseableOnHarvestable {

    public InstantHarverst(String name, Image image) {
        super(name, image);
    }

    public void use(HarvestableCard target) {
        System.out.println("Instant Harvest digunakan");
    }

}
