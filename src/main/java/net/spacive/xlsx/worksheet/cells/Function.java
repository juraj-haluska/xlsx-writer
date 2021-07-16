package net.spacive.xlsx.worksheet.cells;

import net.spacive.xlsx.worksheet.Cell;
import net.spacive.xlsx.worksheet.CellRange;
import net.spacive.xml_core.IStringConsumer;
import net.spacive.xml_core.StringXmlElement;
import net.spacive.xml_core.XmlAttribute;
import net.spacive.xml_core.XmlElement;

import java.util.Locale;

public abstract class Function extends Cell<Function> {

    public static final int SUBTOTAL_FUNCTION_SUM = 9;

    public static Function Subtotal(int subtotalFunction, CellRange cellRange) {

        return new Function() {
            @Override
            protected String renderFunction() {
                return String.format(Locale.US, "SUBTOTAL(%d,%s)", subtotalFunction, cellRange.renderRange());
            }

            @Override
            protected String renderValue() {
                return "";
            }
        };
    }

    public static Function Subtotal(int subtotalFunction, CellRange cellRange, double defaultValue) {

        return new Function() {
            @Override
            protected String renderFunction() {
                return String.format(Locale.US, "SUBTOTAL(%d,%s)", subtotalFunction, cellRange.renderRange());
            }

            @Override
            protected String renderValue() {
                return String.valueOf(defaultValue);
            }
        };
    }

    @Override
    protected Function getSelf() {
        return this;
    }

    @Override
    public void renderInto(IStringConsumer parent) {
        XmlElement cellElement = new XmlElement(parent, "c")
                .addAttribute(new XmlAttribute("r", getCellName()));

        if (getStyle() > 0) {
            cellElement.addAttribute(new XmlAttribute("s", String.valueOf(getStyle())));
        }

        cellElement.open();

        new StringXmlElement(cellElement, "f", renderFunction())
                .open().close();

        String value = renderValue();

        if (!value.isEmpty()) {
            new StringXmlElement(cellElement, "v", value)
                    .open().close();
        }

        cellElement.close();
    }

    protected abstract String renderFunction();

    protected abstract String renderValue();
}
