package libs.Card.Harvestable;

import javax.swing.ImageIcon;

import libs.Card.Card;
import libs.Interfaces.Harvestable;

import java.util.Map;

public abstract class HarvestableCard extends Card implements Harvestable {
    protected ImageIcon transformedImage;
    protected boolean isProtected;
    protected int parameter;
    protected int parameterToHarvest;
    protected String productMade;
    protected Map<String, Integer> appliedEffect;

    public HarvestableCard(String name, ImageIcon image, ImageIcon transformedImage, int parameter,
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

    @Override
    public ImageIcon getImage() {
        if (parameter >= parameterToHarvest) {
            return transformedImage;
        } else {
            return image;
        }
    }
}
