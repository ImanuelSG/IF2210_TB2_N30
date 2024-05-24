package libs.Loader;

import org.json.JSONArray;
import org.json.JSONObject;

import libs.GameWorld.GameWorld;
import libs.Toko.Toko;

import java.io.BufferedReader;
import java.io.FileReader;

public class JsonPlugin implements FilePlugin {

    public void load(String filePath) {

        GameWorld gameWorld = GameWorld.getInstance();
        Toko toko = Toko.getInstance();

        try {
            // Read JSON file content
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            reader.close();

            // Parse JSON
            JSONObject json = new JSONObject(jsonContent.toString());

            // Extract data
            int turn = json.getInt("turn");
            gameWorld.setTurn(turn);

            // Extract toko data
            JSONObject tokoJSON = json.getJSONObject("toko");
            int itemCount = tokoJSON.getInt("itemCount");
            JSONArray items = tokoJSON.getJSONArray("items");
            for (int i = 0; i < itemCount; i++) {
                JSONObject item = items.getJSONObject(i);
                String productName = item.getString("name");
                int quantity = item.getInt("quantity");
                toko.addProduct(productName, quantity);
            }

            // Similarly, extract data for player1 and player2

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean supports(String fileExtension) {
        return "json".equalsIgnoreCase(fileExtension);
    }
}
