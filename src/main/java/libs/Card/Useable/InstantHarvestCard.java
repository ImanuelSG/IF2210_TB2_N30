package libs.Card.Useable;

import javafx.scene.image.Image;
import libs.Card.Card;
import libs.Card.CardFactory;
import libs.Card.Harvestable.HarvestableCard;
import libs.Deck.ActiveDeck;
import libs.Field.Ladang;
import libs.GameWorld.GameWorld;
import libs.Player.Player;

public class InstantHarvestCard extends Card implements UseableOnSelf {

    public InstantHarvestCard(String name, Image image) {
        super(name, image);
    }

    public void use(HarvestableCard target) {
        Player currPlayer = GameWorld.getInstance().getCurrentPlayer();

        Ladang ladang = currPlayer.getField();
        ActiveDeck deck = currPlayer.getActiveDeck();

        if (deck.getRemainingSlot() > 0) {
            String res = target.harvest();
            ladang.removeHarvestable(target);
            deck.add(CardFactory.createProductCard(res));
        } else {
           
        }
    }

}
