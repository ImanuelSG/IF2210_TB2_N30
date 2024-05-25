package libs.FileManager;

import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import libs.GameWorld.GameWorld;
import libs.Toko.Toko;
import libs.Player.Player;
import libs.Deck.*;
import libs.Card.*;
import libs.Card.Harvestable.HarvestableCard;
import libs.Field.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileManager {
    private static FileManager instance;
    private List<FilePlugin> plugins;
    private List<String> supportedExtensions;

    private FileManager() {
        plugins = new ArrayList<>();
        supportedExtensions = new ArrayList<>();
        supportedExtensions.add("txt");
    }

    public static synchronized FileManager getInstance() {
        if (instance == null) {
            instance = new FileManager();
        }
        return instance;
    }

    public List<String> getSupportedExtensions() {
        return supportedExtensions;
    }

    public void loadJar(File jarFile) throws Exception {
        URL jarUrl = jarFile.toURI().toURL();

        try (URLClassLoader classLoader = new URLClassLoader(new URL[] { jarUrl }, FilePlugin.class.getClassLoader());
                JarFile jar = new JarFile(jarFile)) {

            Enumeration<JarEntry> entries = jar.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                if (entry.getName().endsWith(".class")) {
                    String className = entry.getName().replace("/", ".").replace(".class", "");

                    try {
                        Class<?> clazz = classLoader.loadClass(className);

                        if (FilePlugin.class.isAssignableFrom(clazz)) {
                            FilePlugin plugin = (FilePlugin) clazz.getDeclaredConstructor().newInstance();
                            plugins.add(plugin);
                            if (!supportedExtensions.contains(plugin.getSupportedExtension())) {
                                supportedExtensions.add(plugin.getSupportedExtension());
                            }
                            System.out.println("Loaded plugin: " + plugin.getClass().getName());
                            return;
                        }
                    } catch (ClassNotFoundException | NoClassDefFoundError e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            throw new IOException("Failed to load JAR file: " + e.getMessage(), e);
        }

        throw new ClassNotFoundException("No suitable plugin found in JAR file");
    }

    public void loadFile(File file, String extension) {
        if (extension.equals("txt")) {
            loadTxt(file);
        } else {
            for (FilePlugin plugin : plugins) {
                if (plugin.getSupportedExtension().equalsIgnoreCase(extension)) {
                    plugin.load(file);
                    return;
                }
            }
            System.out.println("No plugin found to support the file extension: " + extension);
        }
    }

    public void saveFile(String filePath, String extension) {
        if (extension.equals("txt")) {
            saveTxt(filePath);
        } else {
            for (FilePlugin plugin : plugins) {
                if (plugin.getSupportedExtension().equals(extension)) {
                    plugin.save(filePath);
                    return;
                }
            }
            System.out.println("No plugin found to support the file extension: " + extension);
        }
    }

    public String getFileExtension(String filePath) {
        int dotIndex = filePath.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filePath.length() - 1) {
            return filePath.substring(dotIndex + 1);
        }
        return "";
    }

    public void loadTxt(File file) {
        if (!file.exists() || !file.isFile()) {
            System.out.println("Error: File does not exist.");
            return;
        }

        GameWorld g = GameWorld.getInstance();
        Toko t = Toko.getInstance();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            line = br.readLine();
            int turn = Integer.parseInt(line.trim());
            g.setTurn(turn);

            line = br.readLine();
            int itemCount = Integer.parseInt(line.trim());

            for (int i = 0; i < itemCount; i++) {
                line = br.readLine();
                String[] parts = line.trim().split("\\s+");
                String productName = parts[0];
                int quantity = Integer.parseInt(parts[1]);
                t.addProduct(productName, quantity);
            }

            line = br.readLine();
            int gulden1 = Integer.parseInt(line.trim());

            line = br.readLine();
            int deckCountPlayer1 = Integer.parseInt(line.trim());

            Player player1 = g.getPlayer1();
            player1.setGulden(gulden1);

            Deck deck1 = player1.getDeck();
            deck1.setCards(CardFactory.seedDeck(deckCountPlayer1));

            line = br.readLine();
            int activeDeckCountPlayer1 = Integer.parseInt(line.trim());

            ActiveDeck activeDeckPlayer1 = player1.getActiveDeck();

            for (int j = 0; j < 6; j++) {
                activeDeckPlayer1.removeCard(j);
            }
            activeDeckPlayer1.setCardCount(0);
            for (int j = 0; j < activeDeckCountPlayer1; j++) {
                line = br.readLine();
                String[] parts = line.trim().split("\\s+");
                String location = parts[0];
                String cardName = parts[1];
                Card card = CardFactory.createCard(cardName);
                activeDeckPlayer1.addCard(card, location);
            }

            line = br.readLine();
            int ladangCardCountPlayer1 = Integer.parseInt(line.trim());

            Ladang ladangPlayer1 = player1.getField();
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 5; k++) {
                    ladangPlayer1.removeHarvestable(j, k);
                }
            }
            for (int j = 0; j < ladangCardCountPlayer1; j++) {
                line = br.readLine();
                String[] parts = line.trim().split("\\s+");
                String location = parts[0];

                int col = location.charAt(0) - 'A';
                int row = Integer.parseInt(location.substring(1)) - 1;

                String cardName = parts[1];

                HarvestableCard card = CardFactory.createHarvestableCard(cardName);

                int ageOrWeight = Integer.parseInt(parts[2]);
                card.setParameter(ageOrWeight);

                int activeItemCount = Integer.parseInt(parts[3]);
                for (int i = 4; i < 4 + activeItemCount; i++) {
                    String item = parts[i];
                    card.applyEffect(item);
                }

                ladangPlayer1.setHarvestable(row, col, card);
            }

            line = br.readLine();
            int gulden2 = Integer.parseInt(line.trim());

            line = br.readLine();
            int deckCountPlayer2 = Integer.parseInt(line.trim());

            Player player2 = g.getPlayer2();
            player2.setGulden(gulden2);

            Deck deck2 = player2.getDeck();
            deck2.setCards(CardFactory.seedDeck(deckCountPlayer2));

            line = br.readLine();
            int activeDeckCountPlayer2 = Integer.parseInt(line.trim());

            ActiveDeck activeDeckPlayer2 = player2.getActiveDeck();
            for (int j = 0; j < 6; j++) {
                activeDeckPlayer2.removeCard(j);
            }
            activeDeckPlayer2.setCardCount(0);
            for (int j = 0; j < activeDeckCountPlayer2; j++) {
                line = br.readLine();
                String[] parts = line.trim().split("\\s+");
                String location = parts[0];
                String cardName = parts[1];
                Card card = CardFactory.createCard(cardName);
                activeDeckPlayer2.addCard(card, location);
            }

            line = br.readLine();
            int ladangCardCountPlayer2 = Integer.parseInt(line.trim());

            Ladang ladangPlayer2 = player2.getField();
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 5; k++) {
                    ladangPlayer2.removeHarvestable(j, k);
                }
            }
            for (int j = 0; j < ladangCardCountPlayer2; j++) {
                line = br.readLine();
                String[] parts = line.trim().split("\\s+");
                String location = parts[0];

                int col = location.charAt(0) - 'A';
                int row = Integer.parseInt(location.substring(1)) - 1;

                String cardName = parts[1];

                HarvestableCard card = CardFactory.createHarvestableCard(cardName);

                int ageOrWeight = Integer.parseInt(parts[2]);
                card.setParameter(ageOrWeight);

                int activeItemCount = Integer.parseInt(parts[3]);
                for (int i = 4; i < 4 + activeItemCount; i++) {
                    String item = parts[i];
                    card.applyEffect(item);
                }

                ladangPlayer2.setHarvestable(row, col, card);

                g.setCurrentPlayer(turn % 2 == 0 ? player2 : player1);

            }

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    public void saveTxt(String directory) {
        String filePath = Paths.get(directory, "state.txt").toString();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            GameWorld g = GameWorld.getInstance();
            Toko t = Toko.getInstance();

            // Write current turn
            bw.write(g.getTurn() + "\n");

            // Write shop item count
            bw.write(t.getProductCount() + "\n");

            // Write shop items
            for (Map.Entry<String, Integer> entry : t.getStock().entrySet()) {
                bw.write(entry.getKey() + " " + entry.getValue() + "\n");
            }

            // Write Player 1's data
            Player player1 = g.getPlayer1();
            bw.write(player1.getGulden() + "\n");
            bw.write(player1.getDeck().getSize() + "\n");
            bw.write(player1.getActiveDeck().getCardCount() + "\n");

            ActiveDeck p1ActiveDeck = player1.getActiveDeck();
            for (int i = 0; i < 6; i++) {
                Card activeDeckCard = p1ActiveDeck.getCard(i);
                if (activeDeckCard != null) {
                    bw.write(Ladang.rowColToPetak(0, i) + " ");
                    bw.write(activeDeckCard.getName() + "\n");
                }
            }

            // todo parse ladang info
            Ladang p1Ladang = player1.getField();

            bw.write(p1Ladang.getHarvestableCount() + "\n");

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    HarvestableCard ladangCard = p1Ladang.getHarvestable(i, j);
                    if (ladangCard != null) {
                        bw.write(Ladang.rowColToPetak(i, j) + " ");
                        bw.write(ladangCard.getName() + " ");
                        bw.write(ladangCard.getParameter() + " ");

                        Map<String, Integer> appliedEffects = ladangCard.getAppliedEffect();
                        bw.write(ladangCard.getTotalEffectCount() + " ");

                        for (Map.Entry<String, Integer> entry : appliedEffects.entrySet()) {
                            for (int k = 0; k < entry.getValue(); k++) {
                                bw.write(entry.getKey() + " ");
                            }
                        }

                        bw.write("\n");
                    }
                }
            }

            Player player2 = g.getPlayer2();
            bw.write(player2.getGulden() + "\n");
            bw.write(player2.getDeck().getSize() + "\n");
            bw.write(player2.getActiveDeck().getCardCount() + "\n");

            ActiveDeck p2ActiveDeck = player2.getActiveDeck();
            for (int i = 0; i < 6; i++) {
                Card activeDeckCard = p2ActiveDeck.getCard(i);
                if (activeDeckCard != null) {
                    bw.write(Ladang.rowColToPetak(0, i) + " ");
                    bw.write(activeDeckCard.getName() + "\n");
                }
            }

            // todo parse ladang info
            Ladang p2Ladang = player2.getField();

            bw.write(p2Ladang.getHarvestableCount() + "\n");

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    HarvestableCard ladangCard = p2Ladang.getHarvestable(i, j);
                    if (ladangCard != null) {
                        bw.write(Ladang.rowColToPetak(i, j) + " ");
                        bw.write(ladangCard.getName() + " ");
                        bw.write(ladangCard.getParameter() + " ");

                        Map<String, Integer> appliedEffects = ladangCard.getAppliedEffect();
                        bw.write(ladangCard.getTotalEffectCount() + " ");

                        for (Map.Entry<String, Integer> entry : appliedEffects.entrySet()) {
                            for (int k = 0; k < entry.getValue(); k++) {
                                bw.write(entry.getKey() + " ");
                            }
                        }

                        bw.write("\n");
                    }
                }
            }

            System.out.println("Game state saved successfully to TXT file.");
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
        }
    }
}
