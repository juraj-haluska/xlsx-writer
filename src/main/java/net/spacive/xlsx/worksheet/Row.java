package net.spacive.xlsx.worksheet;

public class Row {

    public static final Range NO_SPAN = new Range(0, 0);
    public static final double DEFAULT_HEIGHT = 0;
    public static final double NO_DY_DESCENT = 0;

    public static class Range {
        private final int start;
        private final int end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }
    }

    public static class Builder {
        private final Row instance;

        public Builder(int rowIndex) {
            this.instance = new Row(rowIndex);
        }

        public Builder setSpan(int start, int end) {
            this.instance.span = new Range(start, end);
            return this;
        }

        public Builder setHeight(double ht) {
            this.instance.height = ht;
            return this;
        }

        public Builder setThickBot() {
            this.instance.isThickBot = true;
            return this;
        }

        public Builder setCustomHeight() {
            this.instance.isCustomHeight = true;
            return this;
        }

        public Builder setDyDescent(double dyDescent) {
            this.instance.dyDescent = dyDescent;
            return this;
        }

        public Row build() {
            return instance;
        }
    }

    private final int rowIndex;

    private Range span = NO_SPAN;
    private double height = DEFAULT_HEIGHT;
    private boolean isThickBot = false;
    private boolean isCustomHeight = false;
    private double dyDescent = NO_DY_DESCENT;

    public static Row atIndex(int rowIndex) {
        return new Row(rowIndex);
    }

    public static Builder at(int rowIndex) {
        return new Builder(rowIndex);
    }

    private Row(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public Range getSpan() {
        return span;
    }

    public double getHeight() {
        return height;
    }

    public boolean isThickBot() {
        return isThickBot;
    }

    public boolean isCustomHeight() {
        return isCustomHeight;
    }

    public double getDyDescent() {
        return dyDescent;
    }
}
