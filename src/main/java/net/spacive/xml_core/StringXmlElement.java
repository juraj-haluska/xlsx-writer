package net.spacive.xml_core;

public class StringXmlElement extends XmlElement {

    private final String content;

    public StringXmlElement(IStringConsumer parent, String tagName, String content) {
        super(parent, tagName);
        this.content = content;
    }

    @Override
    public void close() {
        super.consume(content);
        super.close();
    }
}
