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
        switch (type) {
            case "herbivore":
                System.out.println("Hewan herbivora diberi makan ");
                break;
            case "carnivore":
                System.out.println("Hewan karnivora diberi makan ");
                break;
            default:
                System.out.println("Hewan diberi makan ");
                break;
        }
        System.out.println("Makan hewan");
    }

    @Override
    public void applyEffect(String effect) {
        System.out.println("Hewan diberi efek " + effect);
    }

    @Override
    public String harvest() {
        return this.productMade;
    }

}
