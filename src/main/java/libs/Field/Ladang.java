package libs.Field;

import java.util.ArrayList;

import controller.Observer;
import controller.Observerable;
import libs.Card.Harvestable.HarvestableCard;

public class Ladang implements Observerable {
    private HarvestableCard[][] field;

    private ArrayList<Observer> observers;

    public Ladang() {
        field = new HarvestableCard[4][5];
        observers = new ArrayList<>();
    }

    public HarvestableCard[][] getField() {
        return field;
    }

    public void removeHarvestable(int row, int col) {
        field[row][col] = null;
        notifyObserver();
    }

    public void removeHarvestable(HarvestableCard card) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] != null && field[i][j].equals(card)) {
                    field[i][j] = null;
                    notifyObserver();
                    return;
                }
            }
        }

    }

    public int getHarvestableCount() {
        int count = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] != null) {
                    count++;
                }
            }
        }
        return count;
    }

    public HarvestableCard getHarvestable(int row, int col) {
        return field[row][col];
    }

    public void setHarvestable(int row, int col, HarvestableCard harvestable) {
        field[row][col] = harvestable;
        notifyObserver();
    }

    public HarvestableCard getCardByName(String name) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] != null && field[i][j].getName().equals(name)) {
                    HarvestableCard card = field[i][j];
                    field[i][j] = null;
                    return card;
                }
            }
        }
        return null;
    }

    public static String rowColToPetak(int row, int col) {
        String slot = "";
        slot += intToAlphabet(col);
        slot += intToStringWithLeadingZero(row + 1);
        return slot;
    }

    public static String intToStringWithLeadingZero(int num) {
        if (num < 10) {
            return "0" + Integer.toString(num);
        } else {
            return Integer.toString(num);
        }
    }

    public static String intToAlphabet(int n) {
        if (n < 0 || n >= 26) {
            return "Invalid";
        }

        char alphabet = (char) ('A' + n);

        return Character.toString(alphabet);
    }

    public void moveHarvestable(int fromRow, int fromCol, int toRow, int toCol) {
        field[toRow][toCol] = field[fromRow][fromCol];
        field[fromRow][fromCol] = null;
        notifyObserver();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : observers) {
            observer.updateView();
        }
    }

}
