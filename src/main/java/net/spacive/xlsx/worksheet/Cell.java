package net.spacive.xlsx.worksheet;

import net.spacive.xlsx.IRenderable;
import net.spacive.xlsx.context.XlsxContext;

public abstract class Cell<T extends Cell<?>> implements IRenderable {

    private int row;
    private int column;
    private int verticalSpan;
    private int horizontalSpan;
    private int style;
    private String hyperLink = "";

    public Cell() {
    }

    public Cell(int style) {
        this.style = style;
    }

    protected abstract T getSelf();

    protected void setCoordinates(int row, int column) {
        this.row = row;
        this.column = column;
    }

    protected String getCellName() {
        return XlsxContext.getExcelColumnName(column) + row;
    }

    public T setVerticalSpan(int verticalSpan) {
        this.verticalSpan = verticalSpan;
        return getSelf();
    }

    public T setHorizontalSpan(int horizontalSpan) {
        this.horizontalSpan = horizontalSpan;
        return getSelf();
    }

    public T setSpan(int verticalSpan, int horizontalSpan) {
        this.verticalSpan = verticalSpan;
        this.horizontalSpan = horizontalSpan;
        return getSelf();
    }

    public T setStyle(int style) {
        this.style = style;
        return getSelf();
    }

    public T setHyperlink(String hyperLink) {
        this.hyperLink = hyperLink;
        return getSelf();
    }

    public int getVerticalSpan() {
        return verticalSpan;
    }

    public int getHorizontalSpan() {
        return horizontalSpan;
    }

    public int getStyle() {
        return style;
    }

    public String getHyperLink() {
        return hyperLink;
    }
}
