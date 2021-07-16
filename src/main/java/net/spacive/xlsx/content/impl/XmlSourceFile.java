package net.spacive.xlsx.content.impl;

import net.spacive.xlsx.IRenderable;
import net.spacive.xlsx.content.ISourceFile;
import net.spacive.xml_core.IStringConsumer;
import net.spacive.xml_core.Node;
import net.spacive.xml_core.XmlDocument;

public class XmlSourceFile implements ISourceFile {

    private final String entryName;
    private final IRenderable renderable;

    public XmlSourceFile(String entryName, IRenderable renderable) {
        this.entryName = entryName;
        this.renderable = renderable;
    }

    @Override
    public String getEntryName() {
        return entryName;
    }

    @Override
    public void renderInto(IStringConsumer content) {
        Node document = new XmlDocument(content)
                .setDeclaration("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
                .open();

        renderable.renderInto(document);

        document.close();
    }
}
