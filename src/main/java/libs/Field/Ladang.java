package libs.Field;

import libs.Card.Harvestable.HarvestableCard;

public class Ladang {
    private HarvestableCard[][] field;

    public Ladang() {
        field = new HarvestableCard[4][5];
    }

    public HarvestableCard[][] getField() {
        return field;
    }

    public void removeHarvestable(int row, int col) {
        field[row][col] = null;
    }

    public void removeHarvestable(HarvestableCard card) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] != null && field[i][j].equals(card)) {
                    field[i][j] = null;
                    return;
                }
            }
        }
    }

    public HarvestableCard getHarvestable(int row, int col) {
        return field[row][col];
    }

    public void setHarvestable(int row, int col, HarvestableCard harvestable) {
        field[row][col] = harvestable;
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
}
