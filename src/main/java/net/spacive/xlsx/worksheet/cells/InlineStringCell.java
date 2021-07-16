package net.spacive.xlsx.worksheet.cells;

import net.spacive.xlsx.worksheet.Cell;
import net.spacive.xml_core.*;

import java.util.HashMap;
import java.util.Map;

public class InlineStringCell extends Cell<InlineStringCell> {

    private static final Map<Character, String> ESCAPE_CHARACTERS = new HashMap<>() {{
        put('<', "&lt;");
        put('>', "&gt;");
        put('&', "&amp;");
        put('\"', "&quot;");
        put('\'', "&apos;");
    }};

    private final String string;

    public InlineStringCell(String string) {
        this.string = string;
    }

    public InlineStringCell(String string, int style) {
        super(style);
        this.string = string;
    }

    @Override
    protected InlineStringCell getSelf() {
        return this;
    }

    @Override
    public void renderInto(IStringConsumer consumer) {
        XmlElement cellElement = new XmlElement(consumer, "c")
                .addAttribute(new XmlAttribute("r", getCellName()));

        if (getStyle() > 0) {
            cellElement.addAttribute(new XmlAttribute("s", String.valueOf(getStyle())));
        }

        cellElement.addAttribute(new XmlAttribute("t", "inlineStr"));

        Node cell = cellElement.open();
        Node is = new XmlElement(cell, "is")
                .open();

        new StringXmlElement(is, "t", escapeString(string))
                .open()
                .close();

        is.close();
        cell.close();
    }

    private String escapeString(String string) {
        StringBuilder escapedString = new StringBuilder();

        for (int i = 0; i < string.length(); i++) {
            char currentChar = string.charAt(i);
            String escapeSequence = ESCAPE_CHARACTERS.get(currentChar);
            if (escapeSequence != null) {
                escapedString.append(escapeSequence);
            } else {
                escapedString.append(currentChar);
            }
        }

        return escapedString.toString();
    }
}
