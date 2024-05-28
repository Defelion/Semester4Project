package dk.sdu.sem4.pro.webpage.generatetable;

import java.util.List;

public class CreatedTable {
    private int Row;
    private List<TableCol> tableCols;

    public CreatedTable() {}

    public CreatedTable(int row, List<TableCol> tableCols) {
        Row = row;
        this.tableCols = tableCols;
    }

    public int getRow() {
        return Row;
    }

    public void setRow(int row) {
        Row = row;
    }

    public List<TableCol> getTableCols() {
        return tableCols;
    }

    public void setTableCols(List<TableCol> tableCols) {
        this.tableCols = tableCols;
    }
}
