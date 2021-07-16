package net.spacive.xlsx.renderable;

import net.spacive.xlsx.IRenderable;
import net.spacive.xlsx.rels.Relationship;
import net.spacive.xlsx.rels.RelationshipsHolder;
import net.spacive.xlsx.util.XmlAttributes;
import net.spacive.xml_core.IStringConsumer;
import net.spacive.xml_core.Node;
import net.spacive.xml_core.XmlElement;

import java.util.Map;

public class RelsRenderable implements IRenderable {

    private final Map<Long, Relationship> idRelationshipMap;

    public RelsRenderable(RelationshipsHolder relationshipsHolder) {
        this.idRelationshipMap = relationshipsHolder.getIdRelationshipMap();
    }

    @Override
    public void renderInto(IStringConsumer consumer) {
        Node relsRootElement = new XmlElement(consumer, "Relationships")
                .addAttribute(XmlAttributes.namespace("http://schemas.openxmlformats.org/package/2006/relationships"))
                .open();

        for (Map.Entry<Long, Relationship> entry : idRelationshipMap.entrySet()) {
            XmlElement xmlElement = new XmlElement(relsRootElement, "Relationship")
                    .addAttribute(XmlAttributes.id(entry.getKey()))
                    .addAttribute(XmlAttributes.type(entry.getValue().getType()))
                    .addAttribute(XmlAttributes.target(entry.getValue().getTarget()));

            if (entry.getValue().isExternal()) {
                xmlElement.addAttribute(XmlAttributes.externalTargetMode());
            }

            xmlElement.open().close();
        }

        relsRootElement.close();
    }
}
