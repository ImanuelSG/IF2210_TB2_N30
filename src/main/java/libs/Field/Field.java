package libs.Field;

import libs.Interfaces.Harvestable;

public class Field {
    private Harvestable[][] field;

    public Field(int row, int col) {
        field = new Harvestable[row][col];
    }

    public Harvestable[][] getField() {
        return field;
    }

    public void setField(Harvestable[][] field) {
        this.field = field;
    }

    public void removeHarvestable(int row, int col) {
        field[row][col] = null;
    }

    public Harvestable getHarvestable(int row, int col) {
        return field[row][col];
    }

    public void setHarvestable(int row, int col, Harvestable harvestable) {
        field[row][col] = harvestable;
    }
}
