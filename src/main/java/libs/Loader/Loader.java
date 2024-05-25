package libs.Loader;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Loader {
    private static Loader instance;
    private List<FilePlugin> plugins;

    private Loader() {
        plugins = new ArrayList<>();
    }

    public static synchronized Loader getInstance() {
        if (instance == null) {
            instance = new Loader();
        }
        return instance;
    }

    public void loadJar(String jarFilePath) throws Exception {
        File jarFile = new File(jarFilePath);
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

    public void loadFile(String filePath) {
        String fileExtension = getFileExtension(filePath);
        if (fileExtension.equals("txt")) {
            loadTxt(filePath);
        } else {
            for (FilePlugin plugin : plugins) {
                if (plugin.supports(fileExtension)) {
                    plugin.load(filePath);
                    return;
                }
            }
            System.out.println("No plugin found to support the file extension: " + fileExtension);
        }
    }

    public void saveFile(String filePath) {
        String fileExtension = getFileExtension(filePath);
        if (fileExtension.equals("txt")) {
            saveTxt(filePath);
        } else {
            for (FilePlugin plugin : plugins) {
                if (plugin.supports(fileExtension)) {
                    plugin.save(filePath);
                    return;
                }
            }
            System.out.println("No plugin found to support the file extension: " + fileExtension);
        }
    }

    private String getFileExtension(String filePath) {
        int dotIndex = filePath.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filePath.length() - 1) {
            return filePath.substring(dotIndex + 1);
        }
        return "";
    }

    public void loadTxt(String filePath) {
        File file = new File(filePath);
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

            Player player1 = new Player("player1", gulden1, deckCountPlayer1);
            g.setPlayer1(player1);
        
            line = br.readLine();
            int activeDeckCountPlayer1 = Integer.parseInt(line.trim());

            ActiveDeck activeDeckPlayer1 = new ActiveDeck();
            for (int j = 0; j < activeDeckCountPlayer1; j++) {
                line = br.readLine();
                String[] parts = line.trim().split("\\s+");
                String location = parts[0];
                String cardName = parts[1];
                Card card = CardFactory.createCard(cardName);
                activeDeckPlayer1.addCard(card, location);
            }
            player1.setActiveDeck(activeDeckPlayer1);
    
            line = br.readLine();
            int ladangCardCountPlayer1 = Integer.parseInt(line.trim());
            
            Ladang ladangPlayer1 = new Ladang();
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
            player1.setField(ladangPlayer1);

            line = br.readLine();
            int gulden2 = Integer.parseInt(line.trim());
    
            line = br.readLine();
            int deckCountPlayer2 = Integer.parseInt(line.trim());

            Player player2 = new Player("player2", gulden2, deckCountPlayer2);
            g.setPlayer2(player2);
    
            line = br.readLine();
            int activeDeckCountPlayer2 = Integer.parseInt(line.trim());

            ActiveDeck activeDeckPlayer2 = new ActiveDeck();
            for (int j = 0; j < activeDeckCountPlayer2; j++) {
                line = br.readLine();
                String[] parts = line.trim().split("\\s+");
                String location = parts[0];
                String cardName = parts[1];
                Card card = CardFactory.createCard(cardName);
                activeDeckPlayer2.addCard(card, location);
            }
            player2.setActiveDeck(activeDeckPlayer2);
    
            line = br.readLine();
            int ladangCardCountPlayer2 = Integer.parseInt(line.trim());
            
            Ladang ladangPlayer2 = new Ladang();
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
            }
            player2.setField(ladangPlayer2);
            
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    public void saveTxt(String filepath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
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
