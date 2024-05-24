package libs.Loader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import libs.GameWorld.GameWorld;
import libs.Toko.Toko;
import libs.Player.Player;
import libs.Deck.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

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
        
        try (URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl}, FilePlugin.class.getClassLoader()); JarFile jar = new JarFile(jarFile)) {
            
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
            int gulden = Integer.parseInt(line.trim());

            Player player1 = new Player("player1", gulden);
            g.setPlayer1(player1);
    
            line = br.readLine();
            int deckCountPlayer1 = Integer.parseInt(line.trim());
            // todo fill deck based on count
    
            line = br.readLine();
            int activeDeckCountPlayer1 = Integer.parseInt(line.trim());
            
            ActiveDeck activeDeckPlayer1 = new ActiveDeck();
            for (int j = 0; j < activeDeckCountPlayer1; j++) {
                line = br.readLine();
                String[] parts = line.trim().split("\\s+");
                String location = parts[0];
                String cardName = parts[1];
                // todo create card, put into deck, set player1 deck
            }
    
            line = br.readLine();
            int ladangCardCountPlayer1 = Integer.parseInt(line.trim());
    
            // Assuming each line contains data for one ladang card
            for (int j = 0; j < ladangCardCountPlayer1; j++) {
                line = br.readLine();
                String[] parts = line.trim().split("\\s+");
                // Parse and process ladang card data
                // Example: g.addLadangCardPlayer1(location, cardName, ageOrWeight, activeItemCount, activeItems);
            }

            Player player2 = new Player("player2", gulden);
            g.setPlayer1(player2);
    
            line = br.readLine();
            int deckCountPlayer2 = Integer.parseInt(line.trim());
            // todo fill deck based on count
    
            line = br.readLine();
            int activeDeckCountPlayer2 = Integer.parseInt(line.trim());
            
            ActiveDeck activeDeckPlayer2 = new ActiveDeck();
            for (int j = 0; j < activeDeckCountPlayer2; j++) {
                line = br.readLine();
                String[] parts = line.trim().split("\\s+");
                String location = parts[0];
                String cardName = parts[1];
                // todo create card, put into deck, set player2 deck
            }
    
            line = br.readLine();
            int ladangCardCountPlayer2 = Integer.parseInt(line.trim());
    
            // Assuming each line contains data for one ladang card
            for (int j = 0; j < ladangCardCountPlayer2; j++) {
                line = br.readLine();
                String[] parts = line.trim().split("\\s+");
                // Parse and process ladang card data
                // Example: g.addLadangCardPlayer1(location, cardName, ageOrWeight, activeItemCount, activeItems);
            }
    
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
    
}
