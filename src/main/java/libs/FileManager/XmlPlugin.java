package libs.FileManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.nio.file.Paths;
import java.util.Map;
import libs.GameWorld.GameWorld;
import libs.Toko.Toko;
import libs.Player.Player;
import libs.Deck.ActiveDeck;
import libs.Deck.Deck;
import libs.Card.CardFactory;
import libs.Field.Ladang;
import libs.Card.Harvestable.HarvestableCard;
import libs.Card.Card;

public class XmlPlugin implements FilePlugin {

    @Override
    public void load(File file) {
        try {
            if (!file.exists() || !file.isFile()) {
                System.out.println("Error: File does not exist.");
                return;
            }

            GameWorld gameWorld = GameWorld.getInstance();
            Toko toko = Toko.getInstance();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            Element gameStateElement = document.getDocumentElement();

            int turn = Integer.parseInt(getTagValue("CurrentTurn", gameStateElement));
            gameWorld.setTurn(turn);

            Element shopElement = (Element) gameStateElement.getElementsByTagName("Shop").item(0);
            NodeList itemNodes = shopElement.getElementsByTagName("Item");
            for (int i = 0; i < itemNodes.getLength(); i++) {
                Element itemElement = (Element) itemNodes.item(i);
                String productName = getTagValue("Name", itemElement);
                int quantity = Integer.parseInt(getTagValue("Quantity", itemElement));
                toko.addProduct(productName, quantity);
            }

            Element player1Element = (Element) gameStateElement.getElementsByTagName("Player1").item(0);
            int gulden1 = Integer.parseInt(getTagValue("Gulden", player1Element));
            int deckCount1 = Integer.parseInt(getTagValue("DeckCount", player1Element));
            Player player1 = gameWorld.getPlayer1();
            player1.setGulden(gulden1);

            Deck deck1 = player1.getDeck();
            deck1.setCards(CardFactory.seedDeck(deckCount1));

    
            int activeDeckCount1 = Integer.parseInt(getTagValue("ActiveDeckCount", player1Element));
            ActiveDeck activeDeck1 = player1.getActiveDeck();
            for (int j = 0; j < 6; j++) {
                activeDeck1.removeCard(j);
            }
            activeDeck1.setCardCount(0);           
            NodeList activeDeckNodes1 = player1Element.getElementsByTagName("Card");
            for (int i = 0; i < activeDeckCount1; i++) {
                Element cardElement = (Element) activeDeckNodes1.item(i);
                String location = getTagValue("Location", cardElement);
                String cardName = getTagValue("Name", cardElement);
                activeDeck1.addCard(CardFactory.createCard(cardName), location);
            }
            player1.setActiveDeck(activeDeck1);
    
            // Load ladang (field)
            int ladangCardCount1 = Integer.parseInt(getTagValue("LadangCardCount", player1Element));
            Ladang ladang1 = player1.getField();
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 5; k++) {
                    ladang1.removeHarvestable(j, k);
                }
            }            
            NodeList ladangCardNodes1 = player1Element.getElementsByTagName("LadangCard");
            for (int i = 0; i < ladangCardCount1; i++) {
                Element ladangCardElement = (Element) ladangCardNodes1.item(i);
                String location = getTagValue("Location", ladangCardElement);
                int row = location.charAt(0) - 'A';
                int col = Integer.parseInt(location.substring(1)) - 1;
                
                String cardName = getTagValue("Name", ladangCardElement);
                int ageOrWeight = Integer.parseInt(getTagValue("AgeOrWeight", ladangCardElement));
                HarvestableCard card = CardFactory.createHarvestableCard(cardName);
                card.setParameter(ageOrWeight);

                int activeItemCount = Integer.parseInt(getTagValue("ActiveItemCount", ladangCardElement));
                NodeList activeItemNodes = ladangCardElement.getElementsByTagName("Item");
                for (int j = 0; j < activeItemCount; j++) {
                    String item = activeItemNodes.item(j).getTextContent();
                    card.applyEffect(item);
                }
                ladang1.setHarvestable(row, col, card);
            }
            player1.setField(ladang1);

            Element player2Element = (Element) gameStateElement.getElementsByTagName("Player2").item(0);
            int gulden2 = Integer.parseInt(getTagValue("Gulden", player2Element));
            int deckCount2 = Integer.parseInt(getTagValue("DeckCount", player2Element));
            Player player2 = gameWorld.getPlayer2();
            player2.setGulden(gulden2);

            Deck deck2 = player2.getDeck();
            deck2.setCards(CardFactory.seedDeck(deckCount2));
    
            int activeDeckCount2 = Integer.parseInt(getTagValue("ActiveDeckCount", player2Element));
            ActiveDeck activeDeck2 = player2.getActiveDeck();
            for (int j = 0; j < 6; j++) {
                activeDeck2.removeCard(j);
            }
            activeDeck2.setCardCount(0);
            NodeList activeDeckNodes2 = player2Element.getElementsByTagName("Card");
            for (int i = 0; i < activeDeckCount2; i++) {
                Element cardElement = (Element) activeDeckNodes2.item(i);
                String location = getTagValue("Location", cardElement);
                String cardName = getTagValue("Name", cardElement);
                activeDeck2.addCard(CardFactory.createCard(cardName), location);
            }
            player2.setActiveDeck(activeDeck2);
    
            // Load ladang (field)
            int ladangCardCount2 = Integer.parseInt(getTagValue("LadangCardCount", player2Element));
            Ladang ladang2 = player2.getField();
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 5; k++) {
                    ladang2.removeHarvestable(j, k);
                }
            }
            NodeList ladangCardNodes2 = player2Element.getElementsByTagName("LadangCard");
            for (int i = 0; i < ladangCardCount2; i++) {
                Element ladangCardElement = (Element) ladangCardNodes2.item(i);
                String location = getTagValue("Location", ladangCardElement);
                int col = location.charAt(0) - 'A';
                int row = Integer.parseInt(location.substring(1)) - 1;
                
                String cardName = getTagValue("Name", ladangCardElement);
                int ageOrWeight = Integer.parseInt(getTagValue("AgeOrWeight", ladangCardElement));
                HarvestableCard card = CardFactory.createHarvestableCard(cardName);
                card.setParameter(ageOrWeight);

                int activeItemCount = Integer.parseInt(getTagValue("ActiveItemCount", ladangCardElement));
                NodeList activeItemNodes = ladangCardElement.getElementsByTagName("Item");
                for (int j = 0; j < activeItemCount; j++) {
                    String item = activeItemNodes.item(j).getTextContent();
                    card.applyEffect(item);
                }
                ladang2.setHarvestable(row, col, card);
            }
            player2.setField(ladang2);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: Failed to load game state from XML file.");
        }
    }


    public void save(String directory) {
        try {
            GameWorld g = GameWorld.getInstance();
            Toko t = Toko.getInstance();

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Root element
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("GameState");
            doc.appendChild(rootElement);

            // CurrentTurn element
            Element currentTurn = doc.createElement("CurrentTurn");
            currentTurn.appendChild(doc.createTextNode(String.valueOf(g.getTurn())));
            rootElement.appendChild(currentTurn);

            // Shop element
            Element shopElement = doc.createElement("Shop");
            rootElement.appendChild(shopElement);

            // ItemCount element
            Element itemCount = doc.createElement("ItemCount");
            itemCount.appendChild(doc.createTextNode(String.valueOf(t.getProductCount())));
            shopElement.appendChild(itemCount);

            // Items element
            Element itemsElement = doc.createElement("Items");
            shopElement.appendChild(itemsElement);

            for (Map.Entry<String, Integer> entry : t.getStock().entrySet()) {
                Element item = doc.createElement("Item");

                Element name = doc.createElement("Name");
                name.appendChild(doc.createTextNode(entry.getKey().toUpperCase().replace(" ", "_")));
                item.appendChild(name);

                Element quantity = doc.createElement("Quantity");
                quantity.appendChild(doc.createTextNode(String.valueOf(entry.getValue())));
                item.appendChild(quantity);

                itemsElement.appendChild(item);
            }

            // Player 1
            Player player1 = g.getPlayer1();
            Element player1Element = createPlayerElement(doc, player1, "Player1");
            rootElement.appendChild(player1Element);

            // Player 2
            Player player2 = g.getPlayer2();
            Element player2Element = createPlayerElement(doc, player2, "Player2");
            rootElement.appendChild(player2Element);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            String filePath = Paths.get(directory, "state.xml").toString();
            StreamResult result = new StreamResult(new File(filePath));

            transformer.transform(source, result);

            System.out.println("Game state saved successfully to XML file.");
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private Element createPlayerElement(Document doc, Player player, String playerTag) {
        Element playerElement = doc.createElement(playerTag);
    
        Element gulden = doc.createElement("Gulden");
        gulden.appendChild(doc.createTextNode(String.valueOf(player.getGulden())));
        playerElement.appendChild(gulden);
    
        Element deckCount = doc.createElement("DeckCount");
        deckCount.appendChild(doc.createTextNode(String.valueOf(player.getDeck().getSize())));
        playerElement.appendChild(deckCount);
    
        Element activeDeckCount = doc.createElement("ActiveDeckCount");
        activeDeckCount.appendChild(doc.createTextNode(String.valueOf(player.getActiveDeck().getCardCount())));
        playerElement.appendChild(activeDeckCount);
    
        Element activeDeck = doc.createElement("ActiveDeck");
        playerElement.appendChild(activeDeck);
    
        ActiveDeck activeDeckObj = player.getActiveDeck();
        for (int i = 0; i < 6; i++) {
            Card activeDeckCard = activeDeckObj.getCard(i);
            if (activeDeckCard != null) {
                Element card = doc.createElement("Card");
    
                Element location = doc.createElement("Location");
                location.appendChild(doc.createTextNode(Ladang.rowColToPetak(0, i)));
                card.appendChild(location);
    
                Element name = doc.createElement("Name");
                name.appendChild(doc.createTextNode(activeDeckCard.getName().toUpperCase().replace(" ", "_")));
                card.appendChild(name);
    
                activeDeck.appendChild(card);
            }
        }
    
        Element ladangCardCount = doc.createElement("LadangCardCount");
        ladangCardCount.appendChild(doc.createTextNode(String.valueOf(player.getField().getHarvestableCount())));
        playerElement.appendChild(ladangCardCount);
    
        Element ladangCards = doc.createElement("LadangCards");
        playerElement.appendChild(ladangCards);
    
        Ladang field = player.getField();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                HarvestableCard ladangCard = field.getHarvestable(i, j);
                if (ladangCard != null) {
                    Element ladangCardElement = doc.createElement("LadangCard");
    
                    Element location = doc.createElement("Location");
                    location.appendChild(doc.createTextNode(Ladang.rowColToPetak(i, j)));
                    ladangCardElement.appendChild(location);
    
                    Element name = doc.createElement("Name");
                    name.appendChild(doc.createTextNode(ladangCard.getName().toUpperCase().replace(" ", "_")));
                    ladangCardElement.appendChild(name);
    
                    Element ageOrWeight = doc.createElement("AgeOrWeight");
                    ageOrWeight.appendChild(doc.createTextNode(String.valueOf(ladangCard.getParameter())));
                    ladangCardElement.appendChild(ageOrWeight);
    
                    Element activeItemCount = doc.createElement("ActiveItemCount");
                    activeItemCount.appendChild(doc.createTextNode(String.valueOf(ladangCard.getTotalEffectCount())));
                    ladangCardElement.appendChild(activeItemCount);
    
                    Element activeItems = doc.createElement("ActiveItems");
                    Map<String, Integer> appliedEffects = ladangCard.getAppliedEffect();
                    for (Map.Entry<String, Integer> entry : appliedEffects.entrySet()) {
                        for (int k = 0; k < entry.getValue(); k++) {
                            Element item = doc.createElement("Item");
                            item.appendChild(doc.createTextNode(entry.getKey().toUpperCase().replace(" ", "_")));
                            activeItems.appendChild(item);
                        }
                    }
                    ladangCardElement.appendChild(activeItems);
                    ladangCards.appendChild(ladangCardElement);
                }
            }
        }
        return playerElement;
    }
    


    private String getTagValue(String tag, Element parentElement) {
        NodeList nodeList = parentElement.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            Element element = (Element) nodeList.item(0);
            return element.getTextContent();
        }
        return null;
    }

    @Override
    public String getSupportedExtension() {
        return "xml";
    }
}