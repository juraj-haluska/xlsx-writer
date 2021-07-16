package net.spacive.xml_core;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

public class XmlCoreTest {

    @Test
    public void printXml() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamContentWriter contentWriter = new OutputStreamContentWriter(byteArrayOutputStream);

        Node xmlDocument = new XmlDocument(contentWriter)
                .setDeclaration("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
                .open();

        new XmlElement(xmlDocument, "SomeElement")
                .open()
                .close();

        new XmlElement(xmlDocument, "SomeElementWithAttributes")
                .addAttribute(new XmlAttribute("value","5"))
                .open().close();

        Node node = new XmlElement(xmlDocument, "SomeElementWithChild")
                .open();

        new XmlElement(node, "ChildElement")
                .open()
                .close();

        node.close();

        xmlDocument.close();

        contentWriter.flush();
        System.out.print(byteArrayOutputStream);
    }
}
