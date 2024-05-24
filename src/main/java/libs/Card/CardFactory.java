package libs.Card;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.image.Image;
import libs.Card.Harvestable.AnimalCard;
import libs.Card.Harvestable.HarvestableCard;
import libs.Card.Harvestable.PlantCard;
import libs.Card.Products.ProductCard;
import libs.Card.Useable.*;

public class CardFactory {
    private static CardFactory instance;
    // Map of nama kartu,pathImageAsli,pathImageTransformed,WeightToHarvest,tipe
    // (Karnivor dkk), namaProductHasil

    //
    private static Map<String, ArrayList<String>> MapHewan;
    // Map of nama kartu,pathImageAsli,pathImageTransformed,AgetToHarvest,
    // namaProductHasil
    private static Map<String, ArrayList<String>> MapTanaman;
    // Map of nama kartu, pathImageAsli, harga, penambahanParameter, allowedEater
    private static Map<String, ArrayList<String>> MapProduct;

    private static final Random random = new Random();

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

    public static Card createCard(String name) {
        name = name.replace(" ", "_");
        if (MapTanaman.containsKey(name) || MapHewan.containsKey(name)) {
            return createHarvestableCard(name);
        } else {
            if (MapProduct.containsKey(name)) {
                return createProductCard(name);
            }
            return createItemCard(name);
        }
    }

    public static Card createItemCard(String name) {
        name.replace(" ", "_");
        if (name.equalsIgnoreCase("ACCELERATE")) {
            Image image = new Image("/img/item/accelerate.png");
            return new AccelerateCard(name.replace("_", " "), image);
        } else if (name.equalsIgnoreCase("BEAR_TRAP")) {
            Image image = new Image("img/item/bear_trap.png");
            return new TrapCard(name.replace("_", " "), image);
        } else if (name.equalsIgnoreCase("DELAY")) {
            Image image = new Image("img/item/delay.png");
            return new DelayCard(name.replace("_", " "), image);
        } else if (name.equalsIgnoreCase("DESTROY")) {
            Image image = new Image("img/item/destroy.png");
            return new DestroyCard(name.replace("_", " "), image);
        } else if (name.equalsIgnoreCase("INSTANT_HARVEST")) {
            Image image = new Image("img/item/instant_harvest.png");
            return new InstantHarvestCard(name.replace("_", " "), image);
        } else {
            Image image = new Image("img/item/protect.png");
            return new ProtectCard(name.replace("_", " "), image);
        }
    }

    public static HarvestableCard createHarvestableCard(String name) {
        name = name.replace(" ", "_");

        if (MapHewan.containsKey(name)) {
            return createAnimalCard(name);
        } else {
            return createPlantCard(name);
        }
    }

    private static AnimalCard createAnimalCard(String name) {

        name = name.replace(" ", "_");
        List<String> data = MapHewan.get(name);
        Image image = new Image(data.get(0));
        Image transformedImage = new Image(data.get(1));
        int weightToHarvest = Integer.parseInt(data.get(2));
        String type = data.get(3);
        String productMade = data.get(4);

        return new AnimalCard(name.replace("_", " "), image, transformedImage, 0, weightToHarvest, type, productMade);
    }

    // Factory method to create a ProductCard
    public static ProductCard createProductCard(String name) {
        name = name.replace(" ", "_");
        List<String> data = MapProduct.get(name);
        Image image = new Image(data.get(0));
        int price = Integer.parseInt(data.get(1));
        int addedParameter = Integer.parseInt(data.get(2));
        String type = data.get(3);

        return new ProductCard(name.replace("_", " "), image, price, addedParameter, type);
    }

    private static PlantCard createPlantCard(String name) {
        name = name.replace(" ", "_");
        List<String> data = MapTanaman.get(name);
        Image image = new Image(data.get(0));
        Image transformedImage = new Image(data.get(1));
        int growthTime = Integer.parseInt(data.get(2));
        String productMade = data.get(3);

        return new PlantCard(name.replace("_", " "), image, transformedImage, 0, growthTime, productMade);
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

    public static ArrayList<Card> seedDeck(int size) {
        ArrayList<Card> deck = new ArrayList<>();

        // Calculate the number of cards to add from each map
        int animalCount = Math.min(size / 2, MapHewan.size() * 2);
        int plantCount = Math.min(size / 2, MapTanaman.size() * 2);
        int productCount = Math.min(size / 2, MapProduct.size() * 2);
        int itemCardCount = Math.min(size - animalCount - plantCount - productCount, 12);

        // Add animal cards
        MapHewan.forEach((key, value) -> {
            if (deck.size() < size && deck.size() < animalCount) {
                deck.add(createAnimalCard(key));
                deck.add(createAnimalCard(key));
            }
        });

        // Add plant cards
        MapTanaman.forEach((key, value) -> {
            if (deck.size() < size && deck.size() < animalCount + plantCount) {
                deck.add(createPlantCard(key));
                deck.add(createPlantCard(key));
            }
        });

        // Add product cards
        MapProduct.forEach((key, value) -> {
            if (deck.size() < size && deck.size() < animalCount + plantCount + productCount) {
                deck.add(createProductCard(key));
                deck.add(createProductCard(key));
            }
        });

        // Add item cards
        if (deck.size() < size) {
            for (int i = 0; i < itemCardCount; i++) {
                deck.add(createRandomItemCard());
            }
        }

        return deck;
    }

    public static Card createRandomItemCard() {
        String[] itemNames = { "ACCELERATE", "BEAR_TRAP", "DELAY", "DESTROY", "INSTANT_HARVEST", "PROTECT" };
        String randomItemName = itemNames[random.nextInt(itemNames.length)];
        return createItemCard(randomItemName);
    }
}