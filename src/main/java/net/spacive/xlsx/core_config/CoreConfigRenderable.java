package net.spacive.xlsx.core_config;

import net.spacive.xlsx.IRenderable;
import net.spacive.xlsx.context.XlsxContext;
import net.spacive.xlsx.util.XmlAttributes;
import net.spacive.xml_core.*;

public class CoreConfigRenderable implements IRenderable {

    private final CoreConfig coreConfig;
    private final XlsxContext xlsxContext;

    public CoreConfigRenderable(CoreConfig coreConfig, XlsxContext xlsxContext) {
        this.coreConfig = coreConfig;
        this.xlsxContext = xlsxContext;
    }

    @Override
    public void renderInto(IStringConsumer consumer) {
        Node rootElement = new XmlElement(consumer, "cp:coreProperties")
                .addAttribute(new XmlAttribute("xmlns:cp", "http://schemas.openxmlformats.org/package/2006/metadata/core-properties"))
                .addAttribute(new XmlAttribute("xmlns:dc", "http://purl.org/dc/elements/1.1/"))
                .addAttribute(new XmlAttribute("xmlns:dcterms", "http://purl.org/dc/terms/"))
                .addAttribute(new XmlAttribute("xmlns:dcmitype", "http://purl.org/dc/dcmitype/"))
                .addAttribute(new XmlAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance"))
                .open();

        new StringXmlElement(rootElement, "dc:creator", coreConfig.getCreator()).open().close();
        new StringXmlElement(rootElement, "cp:lastModifiedBy", coreConfig.getLastModifiedBy()).open().close();
        new StringXmlElement(rootElement, "dcterms:created", xlsxContext.formatDate(coreConfig.getCreated()))
                .addAttribute(XmlAttributes.xsiTypeW3CDTF())
                .open().close();
        new StringXmlElement(rootElement, "dcterms:modified", xlsxContext.formatDate(coreConfig.getLastModified()))
                .addAttribute(XmlAttributes.xsiTypeW3CDTF())
                .open().close();

        rootElement.close();
    }
}
