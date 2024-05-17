package libs.Card.Harvestable;

import javax.swing.ImageIcon;

import libs.Interfaces.Harvestable;

import java.util.Map;

public abstract class HarvestableCard implements Harvestable {
    protected String name;
    protected ImageIcon image;
    protected ImageIcon transformedImage;

    protected boolean isProtected;

    protected int parameter;

    protected int parameterToHarvest;

    protected Map<String, Integer> appliedEffect;

    public HarvestableCard(String name, ImageIcon image, ImageIcon transformedImage, int parameter,
            int parameterToHarvest) {
        this.name = name;
        this.image = image;
        this.transformedImage = transformedImage;
        this.parameter = parameter;
        this.parameterToHarvest = parameterToHarvest;
        this.appliedEffect = null;
    }

    @Override
    public boolean isProtected() {
        return isProtected;
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
