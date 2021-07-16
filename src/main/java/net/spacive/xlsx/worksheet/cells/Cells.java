package net.spacive.xlsx.worksheet.cells;

public class Cells {

    public static InlineStringCell string(String inlineContent) {
        return new InlineStringCell(inlineContent);
    }

    public static InlineStringCell string(String inlineContent, int style) {
        return new InlineStringCell(inlineContent, style);
    }

    public static EmptyCell empty() {
        return new EmptyCell();
    }

    public static EmptyCell empty(int style) {
        return new EmptyCell(style);
    }

    public static <T> ValueCell<T> value(T value) {
        return new ValueCell<>(value);
    }

    public static <T> ValueCell<T> value(T value, int style) {
        return new ValueCell<T>(value, style);
    }
}
