package net.spacive.xlsx.renderable;

import net.spacive.xlsx.IRenderable;
import net.spacive.xlsx.content.ContentType;
import net.spacive.xlsx.content.IContentFile;
import net.spacive.xlsx.util.XmlAttributes;
import net.spacive.xml_core.IStringConsumer;
import net.spacive.xml_core.Node;
import net.spacive.xml_core.XmlElement;

import java.util.List;

public class ContentTypesRenderable implements IRenderable {

    private final List<IContentFile> contentFiles;

    public ContentTypesRenderable(List<IContentFile> contentFiles) {
        this.contentFiles = contentFiles;
    }

    @Override
    public void renderInto(IStringConsumer consumer) {
        Node rootElement = new XmlElement(consumer, "Types")
                .addAttribute(XmlAttributes.namespace("http://schemas.openxmlformats.org/package/2006/content-types"))
                .open();

        new XmlElement(rootElement, "Default")
                .addAttribute(XmlAttributes.extension("rels"))
                .addAttribute(XmlAttributes.contentType(ContentType.RELATIONSHIPS))
                .open().close();

        new XmlElement(rootElement, "Default")
                .addAttribute(XmlAttributes.extension("xml"))
                .addAttribute(XmlAttributes.contentType(ContentType.XML))
                .open().close();

        for (IContentFile contentFile: contentFiles) {
            new XmlElement(rootElement, "Override")
                    .addAttribute(XmlAttributes.partName("/" + contentFile.getEntryName()))
                    .addAttribute(XmlAttributes.contentType(contentFile.getContentType()))
                    .open().close();
        }

        rootElement.close();
    }
}
