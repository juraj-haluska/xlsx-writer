package net.spacive.xlsx.worksheet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkSheetParams {

    public static final WorkSheetParams EMPTY = new WorkSheetParams();

    public static class ColumnConfig {
        private final int min;
        private final int max;
        private final double width;

        public ColumnConfig(int minColumn, int maxColumn, double width) {
            this.min = minColumn;
            this.max = maxColumn;
            this.width = width;
        }

        public ColumnConfig(int column, double width) {
            this.min = column;
            this.max = column;
            this.width = width;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

        public double getWidth() {
            return width;
        }
    }

    public static class Builder {
        private double defaultRowHeight = 0;
        private double dyDescent = 0;
        private final List<ColumnConfig> columnConfigList = new ArrayList<>();

        public Builder setDefaultRowHeight(double defaultRowHeight) {
            this.defaultRowHeight = defaultRowHeight;
            return this;
        }

        public Builder setDyDescent(double dyDescent) {
            this.dyDescent = dyDescent;
            return this;
        }

        public Builder addColumnConfig(ColumnConfig columnConfig) {
            this.columnConfigList.add(columnConfig);
            return this;
        }

        public WorkSheetParams build() {
            return new WorkSheetParams(defaultRowHeight, dyDescent, Collections.unmodifiableList(columnConfigList));
        }
    }

    private final double defaultRowHeight;
    private final double dyDescent;
    private final List<ColumnConfig> columnConfigList;

    private WorkSheetParams() {
        defaultRowHeight = 0;
        dyDescent = 0;
        columnConfigList = Collections.emptyList();
    }

    public WorkSheetParams(double defaultRowHeight,
                           double dyDescent,
                           List<ColumnConfig> columnConfigs) {

        this.defaultRowHeight = defaultRowHeight;
        this.dyDescent = dyDescent;
        this.columnConfigList = columnConfigs;
    }

    public double getDefaultRowHeight() {
        return defaultRowHeight;
    }

    public double getDyDescent() {
        return dyDescent;
    }

    public List<ColumnConfig> getColumnConfigList() {
        return columnConfigList;
    }
}
