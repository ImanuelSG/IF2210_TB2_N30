package libs.Card.Harvestable;

import javafx.scene.image.Image;
import libs.Card.Products.ProductCard;

public class AnimalCard extends HarvestableCard {
    private String type;

    public AnimalCard(String name, Image image, Image transformedImage, int weigth, int weigth_to_harvest,
            String type, String productMade) {
        super(name, image, transformedImage, weigth, weigth_to_harvest, productMade);
        this.type = type;
    }

    public void feed(ProductCard product) {
        if (product.use(this)) {
            this.setParameter(this.getParameter() + product.getAddedParameter());
        } else {
            throw new IllegalArgumentException("This animal can't eat this product");
        }
    }

    @Override
    public String harvest() {
        return this.productMade;
    }

    public String getType() {
        return type;
    }

}
