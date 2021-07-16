package net.spacive.xlsx.content.impl;

import net.spacive.xlsx.content.ContentType;
import net.spacive.xlsx.content.IContentFile;
import net.spacive.xml_core.IStringConsumer;

public class StringContentFile implements IContentFile {

    private final ContentType contentType;
    private final String entryName;
    private final String data;

    public StringContentFile(ContentType contentType, String entryName, String data) {
        this.contentType = contentType;
        this.entryName = entryName;
        this.data = data;
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
        consumer.consume(data);
    }
}
