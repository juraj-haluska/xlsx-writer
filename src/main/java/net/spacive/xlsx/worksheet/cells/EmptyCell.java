package net.spacive.xlsx.worksheet.cells;

import net.spacive.xlsx.worksheet.Cell;
import net.spacive.xml_core.IStringConsumer;
import net.spacive.xml_core.XmlAttribute;
import net.spacive.xml_core.XmlElement;

public class EmptyCell extends Cell<EmptyCell> {

    public EmptyCell() {
    }

    public EmptyCell(int style) {
        super(style);
    }

    @Override
    protected EmptyCell getSelf() {
        return this;
    }

    @Override
    public void renderInto(IStringConsumer parent) {
        XmlElement cellElement = new XmlElement(parent, "c")
                .addAttribute(new XmlAttribute("r", getCellName()));

        if (getStyle() > 0) {
            cellElement.addAttribute(new XmlAttribute("s", String.valueOf(getStyle())));
        }

        cellElement.open().close();
    }
}
