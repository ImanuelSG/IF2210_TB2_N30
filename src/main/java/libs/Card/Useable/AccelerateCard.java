package libs.Card.Useable;


import javafx.scene.image.Image;
import libs.Card.Card;
import libs.Card.Harvestable.HarvestableCard;
import libs.Interfaces.UseableOnHarvestable;

public class AccelerateCard extends Card implements UseableOnHarvestable {

    public AccelerateCard(String name, Image image) {
        super(name, image);
    }

    @Override
    public void use(HarvestableCard target) {
        System.out.println("Accelerate Card digunakan");
    }
}
