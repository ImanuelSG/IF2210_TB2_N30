package libs.Card.Harvestable;

import javafx.scene.image.Image;
import libs.Card.Card;
import libs.Interfaces.Harvestable;

import java.util.Map;

public abstract class HarvestableCard extends Card implements Harvestable {
    protected Image transformedImage;
    protected boolean isProtected;
    protected int parameter;
    protected int parameterToHarvest;
    protected String productMade;
    protected Map<String, Integer> appliedEffect;

    public HarvestableCard(String name, Image image, Image transformedImage, int parameter,
            int parameterToHarvest, String productMade) {
        super(name, image);
        this.transformedImage = transformedImage;
        this.parameter = parameter;
        this.parameterToHarvest = parameterToHarvest;
        this.productMade = productMade;
        this.appliedEffect = null;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean isProtected) {
        this.isProtected = isProtected;
    }

    public void setParameter(int parameter) {
        this.parameter = parameter;
    }

    public int getParameter() {
        return parameter;
    }

    public void applyEffect(String effect) {
        if (appliedEffect.containsKey(effect)) {
            appliedEffect.put(effect, appliedEffect.get(effect) + 1);
        } else {
            appliedEffect.put(effect, 1);
        }
    }

    @Override
    public Image getImage() {
        if (parameter >= parameterToHarvest) {
            return transformedImage;
        } else {
            return image;
        }
    }
}
