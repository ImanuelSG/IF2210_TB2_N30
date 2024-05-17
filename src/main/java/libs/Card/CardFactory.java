package libs.Card;

import java.util.Map;

import javax.swing.ImageIcon;

import libs.Card.Harvestable.AnimalCard;
import libs.Card.Harvestable.PlantCard;
import libs.Card.Products.ProductCard;

public class CardFactory {
    private static CardFactory instance;

    private static Map<String,
    private static Map<String, ImageIcon> imageMap;

    private CardFactory() {
        
    }

    // Factory method to create an AnimalCard
    public static AnimalCard createAnimalCard(String name, ImageIcon image, ImageIcon transformedImage, int age,
            int weight,
            int weight_to_harvest) {
        return new AnimalCard(name, image, transformedImage, age, weight, weight_to_harvest);
    }

    // Factory method to create a ProductCard
    public static Produ createProductCard(String name, ImageIcon image) {
        return new ProductCard(name, image);
    }

    // Factory method to create a PlantCard
    public static PlantCard createPlantCard(String name, ImageIcon image, int growthTime) {
        return new PlantCard(name, image, growthTime);
    }
}
