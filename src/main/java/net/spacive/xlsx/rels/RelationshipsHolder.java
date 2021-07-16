package net.spacive.xlsx.rels;

import java.util.HashMap;
import java.util.Map;

public class RelationshipsHolder {

    private final Map<Long, Relationship> idRelationshipMap = new HashMap<>();

    private long idCounter = 0;

    public long addRelationship(Relationship relationship) {
        long relationshipId = ++idCounter;
        idRelationshipMap.put(relationshipId, relationship);
        return relationshipId;
    }

    public Map<Long, Relationship> getIdRelationshipMap() {
        return idRelationshipMap;
    }
}
