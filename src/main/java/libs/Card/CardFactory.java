package libs.Card;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;
import libs.Card.Harvestable.AnimalCard;
import libs.Card.Harvestable.PlantCard;
import libs.Card.Products.ProductCard;

public class CardFactory {
    private static CardFactory instance;
    // Map of nama kartu,pathImageAsli,pathImageTransformed,WeightToHarvest,tipe
    // (Karnivor dkk), namaProductHasil

    //
    public static Map<String, ArrayList<String>> MapHewan;
    // Map of nama kartu,pathImageAsli,pathImageTransformed,AgetToHarvest,
    // namaProductHasil
    public static Map<String, ArrayList<String>> MapTanaman;
    // Map of nama kartu, pathImageAsli, harga, penambahanParameter, allowedEater
    public static Map<String, ArrayList<String>> MapProduct;

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
        Image image = new Image(data.get(0));
        Image transformedImage = new Image(data.get(1));
        int weightToHarvest = Integer.parseInt(data.get(2));
        String type = data.get(3);
        String productMade = data.get(4);

        return new AnimalCard(name, image, transformedImage, 0, weightToHarvest, type, productMade);
    }

    // Factory method to create a ProductCard
    public static ProductCard createProductCard(String name) {

        List<String> data = MapProduct.get(name);
        Image image = new Image(data.get(0));
        int price = Integer.parseInt(data.get(1));
        int addedParameter = Integer.parseInt(data.get(2));
        String type = data.get(3);

        return new ProductCard(name, image, price, addedParameter, type);
    }

    public static PlantCard createPlantCard(String name) {

        List<String> data = MapTanaman.get(name);
        Image image = new Image(data.get(0));
        Image transformedImage = new Image(data.get(1));
        int growthTime = Integer.parseInt(data.get(2));
        String productMade = data.get(3);

        return new PlantCard(name, image, transformedImage, 0, growthTime, productMade);
    }

    private void loadProductMap() {
        MapProduct = new HashMap<>();
        try (InputStream inputStream = getClass().getResourceAsStream("/configs/ProductData.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                ArrayList<String> data = new ArrayList<>();
                for (int i = 1; i < parts.length; i++) {
                    data.add(parts[i]);
                }
                MapProduct.put(parts[0], data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHewanMap() {
        MapHewan = new HashMap<>();
        try (InputStream inputStream = getClass().getResourceAsStream("/configs/AnimalData.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                ArrayList<String> data = new ArrayList<>();
                for (int i = 1; i < parts.length; i++) {
                    data.add(parts[i]);
                }
                MapHewan.put(parts[0], data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTanamanMap() {
        MapTanaman = new HashMap<>();
        try (InputStream inputStream = getClass().getResourceAsStream("/configs/PlantData.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                ArrayList<String> data = new ArrayList<>();
                for (int i = 1; i < parts.length; i++) {
                    data.add(parts[i]);
                }
                MapTanaman.put(parts[0], data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
