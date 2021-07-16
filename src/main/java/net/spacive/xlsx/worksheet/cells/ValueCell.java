package net.spacive.xlsx.worksheet.cells;

import net.spacive.xlsx.worksheet.Cell;
import net.spacive.xml_core.*;

public class ValueCell<T> extends Cell<ValueCell<T>> {

    private final T value;

    public ValueCell(T value) {
        this.value = value;
    }

    public ValueCell(T value, int style) {
        super(style);
        this.value = value;
    }

    @Override
    protected ValueCell<T> getSelf() {
        return this;
    }

    @Override
    public void renderInto(IStringConsumer consumer) {
        XmlElement cellElement = new XmlElement(consumer, "c")
                .addAttribute(new XmlAttribute("r", getCellName()));

        if (getStyle() > 0) {
            cellElement.addAttribute(new XmlAttribute("s", String.valueOf(getStyle())));
        }

        Node cell = cellElement.open();

        new StringXmlElement(cell, "v", String.valueOf(value))
                .open()
                .close();

        cell.close();
    }
}
