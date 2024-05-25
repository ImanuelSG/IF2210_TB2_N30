package libs.Card.Useable;

import javafx.scene.image.Image;
import libs.Card.CardFactory;
import libs.Card.Harvestable.HarvestableCard;
import libs.Deck.ActiveDeck;
import libs.Field.Ladang;
import libs.GameWorld.GameWorld;
import libs.Player.Player;

public class InstantHarvestCard extends UseableOnSelfCard {

    public InstantHarvestCard(String name, Image image) {
        super(name, image);
    }

    @Override
    public void use(HarvestableCard target) {
        Player currPlayer = GameWorld.getInstance().getCurrentPlayer();
        Ladang ladang = currPlayer.getField();
        ActiveDeck deck = currPlayer.getActiveDeck();
        System.out.println("Harvesting " + target.getName());

        if (deck.getRemainingSlot() > 0) {

            System.out.println("Harvesting " + target.getName());
            String res = target.harvest();
            ladang.removeHarvestable(target);
            deck.add(CardFactory.createProductCard(res));
        } else {
            throw new IllegalArgumentException("Deck is full");
        }
    }

}
