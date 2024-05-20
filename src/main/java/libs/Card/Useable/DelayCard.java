package libs.Card.Useable;

import libs.Card.Card;
import libs.Card.Harvestable.AnimalCard;
import libs.Card.Harvestable.HarvestableCard;
import libs.Card.Harvestable.PlantCard;
import libs.Interfaces.UseableOnHarvestable;

public class DelayCard extends Card implements UseableOnHarvestable {

    public DelayCard(String name, javafx.scene.image.Image image) {
        super(name, image); 
    }

    @Override
    public void use(HarvestableCard target) {
        if (target instanceof AnimalCard) {
            target.setParameter(target.getParameter() > 5 ? target.getParameter() - 5 : 0);

        } else if (target instanceof PlantCard) {
            target.setParameter(target.getParameter() > 2 ? target.getParameter() - 2 : 0);
        }
        target.applyEffect("Delay");
    }
}
