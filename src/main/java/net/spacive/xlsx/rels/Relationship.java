package net.spacive.xlsx.rels;

public class Relationship {

    private final String type;
    private final String target;
    private final boolean isExternal;

    public Relationship(String type, String target, boolean isExternal) {
        this.type = type;
        this.target = target;
        this.isExternal = isExternal;
    }

    public String getType() {
        return type;
    }

    public String getTarget() {
        return target;
    }

    public boolean isExternal() {
        return isExternal;
    }
}
