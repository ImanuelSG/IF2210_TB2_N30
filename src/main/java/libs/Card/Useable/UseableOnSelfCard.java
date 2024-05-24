package libs.Card.Useable;

import javafx.scene.image.Image;
import libs.Card.Card;

public abstract class UseableOnSelfCard extends Card implements UseableOnSelf {
    public UseableOnSelfCard(String name, Image image) {
        super(name, image);
    }
}
