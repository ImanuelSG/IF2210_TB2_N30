package libs.Card.Useable;

import libs.Card.Harvestable.AnimalCard;
import libs.Card.Harvestable.HarvestableCard;
import libs.Card.Harvestable.PlantCard;
import libs.Player.Player;

public class DelayCard extends UseableOnEnemyCard {

    public DelayCard(String name, javafx.scene.image.Image image) {
        super(name, image);
    }

    @Override
    public void use(HarvestableCard target, Player enemyPlayer) {
        if (target.isProtected()) {
            throw new IllegalArgumentException("Tidak bisa menggunakan kartu Delay");
        } else {
            if (target instanceof AnimalCard) {
                target.setParameter(target.getParameter() > 5 ? target.getParameter() - 5 : 0);

            } else if (target instanceof PlantCard) {
                target.setParameter(target.getParameter() > 2 ? target.getParameter() - 2 : 0);
            }
            target.applyEffect("Delay");
        }
    }
}
