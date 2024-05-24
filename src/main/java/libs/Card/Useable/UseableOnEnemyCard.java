package libs.Card.Useable;

import javafx.scene.image.Image;
import libs.Card.Card;

public abstract class UseableOnEnemyCard extends Card implements UseableOnEnemy {
    public UseableOnEnemyCard(String name, Image image) {
        super(name, image);
    }
}
