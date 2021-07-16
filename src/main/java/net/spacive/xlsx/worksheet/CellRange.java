package net.spacive.xlsx.worksheet;

import net.spacive.xlsx.context.XlsxContext;

import java.util.Locale;

public class CellRange {

    private final int startRow;
    private final int endRow;
    private final int startColumn;
    private final int endColumn;

    public static CellRange sameColumn(int column, int startRow, int endRow) {
        return new CellRange(startRow, endRow, column, column);
    }

    public CellRange(int startRow, int endRow, int startColumn, int endColumn) {
        this.startRow = startRow;
        this.endRow = endRow;
        this.startColumn = startColumn;
        this.endColumn = endColumn;
    }

    public String renderRange() {
        return String.format(Locale.US, "%s%d:%s%d",
                XlsxContext.getExcelColumnName(startColumn),
                startRow,
                XlsxContext.getExcelColumnName(endColumn),
                endRow);
    }
}
