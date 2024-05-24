package libs.Card.Useable;

import libs.Card.Harvestable.HarvestableCard;
import libs.Field.Ladang;
import libs.Player.Player;
import javafx.scene.image.Image;

public class DestroyCard extends UseableOnEnemyCard {
    public DestroyCard(String name, Image image) {
        super(name, image);
    }

    @Override
    public void use(HarvestableCard target, Player player) {
        Ladang enemyField = player.getField();

        enemyField.removeHarvestable(target);

    }

}
