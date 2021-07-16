package net.spacive.xml_core;

import java.util.ArrayList;
import java.util.List;

public class XmlElement extends Node {

    private static final String SPACE = " ";

    private final String tagName;
    private final List<XmlAttribute> xmlAttributes = new ArrayList<>();

    private boolean hasChildContent = false;

    public XmlElement(IStringConsumer parent, String tagName) {
        super(parent);
        this.tagName = tagName;
    }

    public XmlElement addAttribute(XmlAttribute xmlAttribute) {
        this.xmlAttributes.add(xmlAttribute);
        return this;
    }

    @Override
    protected String getNodeOpening() {
        StringBuilder builder = new StringBuilder()
                .append("<")
                .append(tagName);

        for (XmlAttribute xmlAttribute : xmlAttributes) {
            appendAttribute(builder, xmlAttribute);
        }

        return builder.toString();
    }

    @Override
    protected String getNodeClosing() {
        if (this.hasChildContent) {
            return "</" + this.tagName + ">" + System.lineSeparator();
        } else {
            return "/>" + System.lineSeparator();
        }
    }

    private void appendAttribute(StringBuilder builder, XmlAttribute xmlAttribute) {
        builder.append(SPACE)
                .append(xmlAttribute.getName())
                .append("=\"")
                .append(xmlAttribute.getValue())
                .append("\"");
    }

    @Override
    public void consume(String content) {
        if (!this.hasChildContent) {
            super.consume(">" + System.lineSeparator());
            this.hasChildContent = true;
        }

        super.consume(content);
    }
}
