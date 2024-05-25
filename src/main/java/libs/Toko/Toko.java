package libs.Toko;

import java.util.HashMap;
import java.util.Map;

public class Toko {
    private Map<String, Integer> stock;
    // Private static instance of the singleton class
    private static Toko instance;

    // Private constructor to prevent instantiation
    private Toko() {
        stock = new HashMap<>();
    }

    // Public static method to provide access to the singleton instance
    public static synchronized Toko getInstance() {
        if (instance == null) {
            instance = new Toko();
        }
        return instance;
    }

    // Method to add products to the stock
    public void addProduct(String productName, int quantity) {
        stock.put(productName, stock.getOrDefault(productName, 0) + quantity);
    }


    // Method to remove products from the stock
    public boolean removeProduct(String productName, int quantity) {
        if (stock.containsKey(productName) && stock.get(productName) >= quantity) {
            stock.put(productName, stock.get(productName) - quantity);
            return true;
        }
        return false;
    }

    // Method to get the stock of a product
    public int getProductStock(String productName) {
        return stock.getOrDefault(productName, 0);
    }

    // Method to get the entire stock
    public Map<String, Integer> getStock() {
        return new HashMap<>(stock);
    }

    public int buy(String productName, int quantity) {
        if (removeProduct(productName, quantity)) {
            System.out.println("Pembelian berhasil");
            return 0;
        } else {
            System.out.println("Pembelian gagal");
            return -1;
        }
    }

    public int sell(String productName, int quantity) {
        addProduct(productName, quantity);
        System.out.println("Penjualan berhasil");
        return 0;
    }

    public void setItems(Map<String, Integer> items) {
        stock = new HashMap<>(items);
    }

    public int getProductCount() {
        return stock.size();
    }
}
