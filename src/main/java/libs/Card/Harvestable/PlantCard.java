package libs.Card.Harvestable;

import javafx.scene.image.Image;

public class PlantCard extends HarvestableCard {

    public PlantCard(String name, Image image, Image transformedImage, int age, int age_to_harvest,
            String productMade) {
        super(name, image, transformedImage, age, age_to_harvest, productMade);
    }

    public void addPlantAge() {
        this.parameter += 1;
    }

    @Override
    public String harvest() {
        return this.productMade;
    }

}
