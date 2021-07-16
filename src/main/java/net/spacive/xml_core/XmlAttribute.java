package net.spacive.xml_core;

public class XmlAttribute {

    private final String name;
    private final String value;

    public XmlAttribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
