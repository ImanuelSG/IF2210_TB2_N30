package libs.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import libs.Card.Harvestable.AnimalCard;
import libs.Card.Harvestable.PlantCard;
import libs.Card.Products.ProductCard;

public class CardFactory {
    private static CardFactory instance;
    // Map of nama kartu,pathImageAsli,pathImageTransformed,WeightToHarvest,tipe
    // (Karnivor dkk), namaProductHasil
    private static Map<String, ArrayList<String>> MapHewan;
    // Map of nama kartu,pathImageAsli,pathImageTransformed,AgetToHarvest,
    // namaProductHasil
    private static Map<String, ArrayList<String>> MapTanaman;
    // Map of nama kartu, pathImageAsli, harga, penambahanParameter, allowedEater
    private static Map<String, ArrayList<String>> MapProduct;

    private CardFactory() {
        loadProductMap();
        loadHewanMap();
        loadTanamanMap();
    }

    public static CardFactory getInstance() {
        if (instance == null) {
            instance = new CardFactory();
        }
        return instance;
    }

    public static AnimalCard createAnimalCard(String name) {
        List<String> data = MapHewan.get(name);
        ImageIcon image = new ImageIcon(data.get(0));
        ImageIcon transformedImage = new ImageIcon(data.get(1));
        int weightToHarvest = Integer.parseInt(data.get(2));
        String type = data.get(3);
        String productMade = data.get(4);

        return new AnimalCard(name, image, transformedImage, 0, weightToHarvest, type, productMade);
    }

    // Factory method to create a ProductCard
    public static ProductCard createProductCard(String name) {

        List<String> data = MapProduct.get(name);
        ImageIcon image = new ImageIcon(data.get(0));
        int price = Integer.parseInt(data.get(1));
        int addedParameter = Integer.parseInt(data.get(2));
        String type = data.get(3);

        return new ProductCard(name, image, price, addedParameter, type);
    }

    public static PlantCard createPlantCard(String name) {

        List<String> data = MapTanaman.get(name);
        ImageIcon image = new ImageIcon(data.get(0));
        ImageIcon transformedImage = new ImageIcon(data.get(1));
        int growthTime = Integer.parseInt(data.get(2));
        String productMade = data.get(3);

        return new PlantCard(name, image, transformedImage, 0, growthTime, productMade);
    }

    private void loadProductMap() {
        MapProduct = new HashMap<>();

    }

    private void loadHewanMap() {
        MapHewan = new HashMap<>();
        MapHewan.put("Ayam", new ArrayList<String>());

    }

    private void loadTanamanMap() {
        MapProduct = new HashMap<>();
        MapProduct.put("Produk", new ArrayList<String>());
    }

}
