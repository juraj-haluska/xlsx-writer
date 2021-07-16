package net.spacive.xlsx.content.impl;

import net.spacive.xlsx.IRenderable;
import net.spacive.xlsx.content.ContentType;
import net.spacive.xlsx.content.IContentFile;
import net.spacive.xml_core.IStringConsumer;
import net.spacive.xml_core.Node;
import net.spacive.xml_core.XmlDocument;

public class XmlContentFile<T extends IRenderable> implements IContentFile {

    private final ContentType contentType;
    private final String entryName;
    private final T renderable;

    public XmlContentFile(ContentType contentType, String entryName, T renderable) {
        this.contentType = contentType;
        this.entryName = entryName;
        this.renderable = renderable;
    }

    @Override
    public ContentType getContentType() {
        return contentType;
    }

    @Override
    public String getEntryName() {
        return entryName;
    }

    @Override
    public void renderInto(IStringConsumer consumer) {
        Node document = new XmlDocument(consumer)
                .setDeclaration("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
                .open();

        renderable.renderInto(document);

        document.close();
    }
}
