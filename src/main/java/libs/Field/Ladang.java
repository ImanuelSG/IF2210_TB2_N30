package libs.Field;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import libs.Card.Harvestable.HarvestableCard;


public class Ladang {
    private ObjectProperty<HarvestableCard>[][] field;

    @SuppressWarnings("unchecked")
    public Ladang(int row, int col) {
        field = new SimpleObjectProperty[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                field[i][j] = new SimpleObjectProperty<>();
            }
        }
    }

    public ObjectProperty<HarvestableCard>[][] getField() {
        return field;
    }

    public void removeHarvestable(int row, int col) {
        field[row][col].set(null);
    }

    public HarvestableCard getHarvestable(int row, int col) {
        return field[row][col].get();
    }

    public void setHarvestable(int row, int col, HarvestableCard harvestable) {
        field[row][col].set(harvestable);
    }

    public HarvestableCard getCardByName(String name) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] != null && field[i][j].getName().equals(name)) {
                    HarvestableCard card = field[i][j].get();
                    field[i][j] = null;
                    return card;
                }
            }
        }
        return null;
    }
}
