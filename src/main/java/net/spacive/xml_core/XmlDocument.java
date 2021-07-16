package net.spacive.xml_core;

public class XmlDocument extends Node {

    private String declaration;

    public XmlDocument(IStringConsumer parent) {
        super(parent);
    }

    public XmlDocument setDeclaration(String declaration) {
        this.declaration = declaration;
        return this;
    }

    @Override
    protected String getNodeOpening() {
        return declaration + System.lineSeparator();
    }

    @Override
    protected String getNodeClosing() {
        return "";
    }
}
