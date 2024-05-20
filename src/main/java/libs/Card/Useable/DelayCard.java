package libs.Card.Useable;

import javax.swing.ImageIcon;

import libs.Card.Card;
import libs.Card.Harvestable.HarvestableCard;
import libs.Interfaces.UseableOnHarvestable;

public class DelayCard extends Card implements UseableOnHarvestable {

    public DelayCard(String name, ImageIcon image) {
        super(name, image);
    }

    @Override
    public void use(HarvestableCard target) {
        System.out.println("Delay Card digunakan");
    }
}
