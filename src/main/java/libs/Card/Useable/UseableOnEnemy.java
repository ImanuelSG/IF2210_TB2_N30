package libs.Card.Useable;

import libs.Card.Harvestable.HarvestableCard;
import libs.Player.Player;

public interface UseableOnEnemy {
    void use(HarvestableCard target, Player enemy);
}
