package libs.FileManager;

import org.json.JSONArray;
import org.json.JSONObject;

import libs.GameWorld.GameWorld;
import libs.Toko.Toko;
import libs.Player.Player;
import libs.Deck.ActiveDeck;
import libs.Card.CardFactory;
import libs.Field.Ladang;
import libs.Card.Harvestable.HarvestableCard;
import libs.Card.Card;

import java.io.*;
import java.nio.file.Paths;
import java.util.Map;

public class JsonPlugin implements FilePlugin {
    public void load(File file) {
        GameWorld gameWorld = GameWorld.getInstance();
        Toko toko = Toko.getInstance();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            reader.close();

            JSONObject json = new JSONObject(jsonContent.toString());

            int turn = json.getInt("turn");
            gameWorld.setTurn(turn);

            JSONObject tokoJSON = json.getJSONObject("toko");
            int itemCount = tokoJSON.getInt("itemCount");
            JSONArray items = tokoJSON.getJSONArray("items");
            for (int i = 0; i < itemCount; i++) {
                JSONObject item = items.getJSONObject(i);
                String productName = item.getString("name");
                int quantity = item.getInt("quantity");
                toko.addProduct(productName, quantity);
            }

            JSONObject player1JSON = json.getJSONObject("player1");
            loadPlayerData(player1JSON, gameWorld, "player1");

            JSONObject player2JSON = json.getJSONObject("player2");
            loadPlayerData(player2JSON, gameWorld, "player2");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: Failed to load game state from JSON file.");
        }
    }

    private void loadPlayerData(JSONObject playerJSON, GameWorld gameWorld, String playerName) {
        int gulden = playerJSON.getInt("gulden");
        int deckCount = playerJSON.getInt("deckCount");
        Player player = new Player(playerName, gulden, deckCount);

        if (playerName.equals("player1")) {
            gameWorld.setPlayer1(player);
        } else {
            gameWorld.setPlayer2(player);
        }

        int activeDeckCount = playerJSON.getInt("activeDeckCount");
        ActiveDeck activeDeck = new ActiveDeck();
        JSONArray activeDeckArray = playerJSON.getJSONArray("activeDeck");
        for (int i = 0; i < activeDeckCount; i++) {
            JSONObject cardObject = activeDeckArray.getJSONObject(i);
            String location = cardObject.getString("location");
            String cardName = cardObject.getString("card");
            activeDeck.addCard(CardFactory.createCard(cardName), location);
        }
        player.setActiveDeck(activeDeck);

        int ladangCardCount = playerJSON.getInt("ladangCardCount");
        Ladang ladang = new Ladang();
        JSONArray ladangCardsArray = playerJSON.getJSONArray("ladangCards");
        for (int i = 0; i < ladangCardCount; i++) {
            JSONObject ladangCardObject = ladangCardsArray.getJSONObject(i);
            String location = ladangCardObject.getString("location");
            int col = location.charAt(0) - 'A';
            int row = Integer.parseInt(location.substring(1)) - 1;
            String cardName = ladangCardObject.getString("card");
            int ageOrWeight = ladangCardObject.getInt("ageOrWeight");
            HarvestableCard card = CardFactory.createHarvestableCard(cardName);
            card.setParameter(ageOrWeight);
            int activeItemCount = ladangCardObject.getInt("activeItemCount");
            JSONArray activeItemsArray = ladangCardObject.getJSONArray("activeItems");
            for (int j = 0; j < activeItemCount; j++) {
                String item = activeItemsArray.getString(j);
                card.applyEffect(item);
            }
            ladang.setHarvestable(row, col, card);
        }
        player.setField(ladang);
    }

    public void save(String directory) {
        GameWorld g = GameWorld.getInstance();
        Toko t = Toko.getInstance();

        JSONObject gameState = new JSONObject();

        gameState.put("turn", g.getTurn());

        JSONObject toko = new JSONObject();
        toko.put("itemCount", t.getProductCount());

        JSONArray itemsArray = new JSONArray();
        for (Map.Entry<String, Integer> entry : t.getStock().entrySet()) {
            JSONObject item = new JSONObject();
            item.put("name", entry.getKey());
            item.put("quantity", entry.getValue());
            itemsArray.put(item);
        }
        toko.put("items", itemsArray);
        gameState.put("toko", toko);

        Player player1 = g.getPlayer1();
        gameState.put("player1", createPlayerJson(player1));

        Player player2 = g.getPlayer2();
        gameState.put("player2", createPlayerJson(player2));

        String filePath = Paths.get(directory, "state.txt").toString();

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(gameState.toString(4));
            System.out.println("Game state saved successfully to JSON file.");
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
        }
    }

    private JSONObject createPlayerJson(Player player) {
        JSONObject playerJson = new JSONObject();

        playerJson.put("gulden", player.getGulden());
        playerJson.put("deckCount", player.getDeck().getSize());
        playerJson.put("activeDeckCount", player.getActiveDeck().getCardCount());

        JSONArray activeDeckArray = new JSONArray();
        ActiveDeck activeDeckObj = player.getActiveDeck();
        for (int i = 0; i < 6; i++) {
            Card activeDeckCard = activeDeckObj.getCard(i);
            if (activeDeckCard != null) {
                JSONObject card = new JSONObject();
                card.put("location", Ladang.rowColToPetak(0, i));
                card.put("card", activeDeckCard.getName());
                activeDeckArray.put(card);
            }
        }
        playerJson.put("activeDeck", activeDeckArray);

        playerJson.put("ladangCardCount", player.getField().getHarvestableCount());

        JSONArray ladangCardsArray = new JSONArray();
        Ladang field = player.getField();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                HarvestableCard ladangCard = field.getHarvestable(i, j);
                if (ladangCard != null) {
                    JSONObject ladangCardJson = new JSONObject();
                    ladangCardJson.put("location", Ladang.rowColToPetak(i, j));
                    ladangCardJson.put("card", ladangCard.getName());
                    ladangCardJson.put("ageOrWeight", ladangCard.getParameter());
                    ladangCardJson.put("activeItemCount", ladangCard.getTotalEffectCount());

                    JSONArray activeItemsArray = new JSONArray();
                    Map<String, Integer> appliedEffects = ladangCard.getAppliedEffect();
                    for (Map.Entry<String, Integer> entry : appliedEffects.entrySet()) {
                        for (int k = 0; k < entry.getValue(); k++) {
                            activeItemsArray.put(entry.getKey());
                        }
                    }
                    ladangCardJson.put("activeItems", activeItemsArray);
                    ladangCardsArray.put(ladangCardJson);
                }
            }
        }
        playerJson.put("ladangCards", ladangCardsArray);

        return playerJson;
    }


    @Override
    public boolean supports(String fileExtension) {
        return "json".equalsIgnoreCase(fileExtension);
    }
}
