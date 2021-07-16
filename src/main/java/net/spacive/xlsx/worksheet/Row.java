package net.spacive.xlsx.worksheet;

import net.spacive.xml_core.Node;

public class Row {

    private final RowParams rowParams;
    private final Node node;
    private final WorkSheet workSheet;

    private boolean isClosed = false;

    public Row(RowParams rowParams, Node node, WorkSheet workSheet) {
        this.rowParams = rowParams;
        this.node = node;
        this.workSheet = workSheet;
    }

    public Row putCell(int columnNumber, Cell<?> cell) {
        if (columnNumber <= 0) {
            throw new RuntimeException("column number must be > 0 but provided value was " + columnNumber);
        }

        int row = rowParams.getRowNumber();

        cell.setCoordinates(row, columnNumber);
        cell.renderInto(node);

        if (cell.getHorizontalSpan() > 0 || cell.getVerticalSpan() > 0) {
            int endRow = row + cell.getVerticalSpan();
            int endColumn = columnNumber + cell.getHorizontalSpan();

            workSheet.registerMergedCells(new CellRange(row, endRow, columnNumber, endColumn));
        }

        if (!cell.getHyperLink().isEmpty()) {
            workSheet.registerHyperlink(cell.getHyperLink(), row, columnNumber);
        }

        return this;
    }

    public void close() {
        this.node.close();
        this.isClosed = true;
    }

    public boolean isClosed() {
        return this.isClosed;
    }
}
