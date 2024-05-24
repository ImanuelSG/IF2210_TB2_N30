package libs.Loader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import libs.GameWorld.GameWorld;
import libs.Toko.Toko;

public class XmlPlugin implements FilePlugin {
    public void load(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            System.out.println("Error: File does not exist.");
            return;
        }
    
        GameWorld gameWorld = GameWorld.getInstance();
        Toko toko = Toko.getInstance();
    
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
    
            Element gameStateElement = (Element) document.getElementsByTagName("GameState").item(0);
    
            String turnStr = getTagValue("CurrentTurn", document);
            int turn = Integer.parseInt(turnStr);
            gameWorld.setTurn(turn);
    
            Element shopElement = (Element) gameStateElement.getElementsByTagName("Shop").item(0);
            NodeList itemNodes = shopElement.getElementsByTagName("Item");
            for (int i = 0; i < itemNodes.getLength(); i++) {
                Element itemElement = (Element) itemNodes.item(i);
                String productName = getTagValue("Name", itemElement);
                String productQuantityStr = getTagValue("Quantity", itemElement);
                if (productName != null && productQuantityStr != null) {
                    int quantity = Integer.parseInt(productQuantityStr);
                    toko.addProduct(productName, quantity);
                }
            }
    
            // Load player data
            // NodeList playerNodes = gameStateElement.getElementsByTagName("Player");
            // for (int i = 0; i < playerNodes.getLength(); i++) {
            //     Element playerElement = (Element) playerNodes.item(i);
            //     String playerName = playerElement.getTagName();
            //     int gulden = Integer.parseInt(getTagValue("Gulden", playerElement));
            //     // Load other player data and update GameWorld or Toko accordingly
            //     // Example: gameWorld.setPlayerGulden(playerName, gulden);
            // }
    
            System.out.println("Game state loaded successfully from XML file.");
    
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: Failed to load game state from XML file.");
        }
    }
    
    private String getTagValue(String tag, Element parentElement) {
        NodeList nodeList = parentElement.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            Element element = (Element) nodeList.item(0);
            return element.getTextContent();
        }
        return null;
    }

    private String getTagValue(String tag, Document document) {
        NodeList nodeList = document.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            Element element = (Element) nodeList.item(0);
            return element.getTextContent();
        }
        return null;
    }

    public boolean supports(String fileExtension) {
        return "xml".equalsIgnoreCase(fileExtension);
    }
}
