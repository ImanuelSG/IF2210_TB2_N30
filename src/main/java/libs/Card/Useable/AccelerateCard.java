package libs.Card.Useable;

import javafx.scene.image.Image;
import libs.Card.Harvestable.AnimalCard;
import libs.Card.Harvestable.HarvestableCard;
import libs.Card.Harvestable.PlantCard;

public class AccelerateCard extends UseableOnSelfCard {
    public AccelerateCard(String name, Image image) {
        super(name, image);
    }

    @Override
    public void use(HarvestableCard target) {
        if (target instanceof AnimalCard) {
            target.setParameter(target.getParameter() + 8);

        } else if (target instanceof PlantCard) {
            target.setParameter(target.getParameter() + 2);
        }
        target.applyEffect("Accelerate");
    }
}
