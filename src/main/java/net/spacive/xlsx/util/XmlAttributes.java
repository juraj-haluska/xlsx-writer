package net.spacive.xlsx.util;

import net.spacive.xlsx.content.ContentType;
import net.spacive.xml_core.XmlAttribute;

public class XmlAttributes {

    public static XmlAttribute namespace(String namespace) {
        return new XmlAttribute("xmlns", namespace);
    }

    public static XmlAttribute id(long id) {
        return new XmlAttribute("Id", "rId" + id);
    }

    public static XmlAttribute type(String uri) {
        return new XmlAttribute("Type", uri);
    }

    public static XmlAttribute target(String uri) {
        return new XmlAttribute("Target", uri);
    }

    public static XmlAttribute externalTargetMode() {
        return new XmlAttribute("TargetMode", "External");
    }

    public static XmlAttribute extension(String extension) {
        return new XmlAttribute("Extension", extension);
    }

    public static XmlAttribute contentType(ContentType contentType) {
        return new XmlAttribute("ContentType", contentType.toString());
    }

    public static XmlAttribute partName(String partName) {
        return new XmlAttribute("PartName", partName);
    }

    public static XmlAttribute xsiTypeW3CDTF() {
        return new XmlAttribute("xsi:type", "dcterms:W3CDTF");
    }
}
