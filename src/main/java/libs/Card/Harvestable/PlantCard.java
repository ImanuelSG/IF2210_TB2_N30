package libs.Card.Harvestable;

import javax.swing.ImageIcon;

public class PlantCard extends HarvestableCard {

    public PlantCard(String name, ImageIcon image, ImageIcon transformedImage, int age, int age_to_harvest,
            String productMade) {
        super(name, image, transformedImage, age, age_to_harvest, productMade);
    }

    public void addPlantAge() {
        this.parameter += 1;
    }

    @Override
    public void applyEffect(String effect) {
        System.out.println("Tanaman diberi efek " + effect);
    }

    @Override
    public String harvest() {
        return this.productMade;

    }

}
