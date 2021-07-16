package net.spacive.xlsx;

public class Sheet {
    private final String name;
    private final int sheetId;
    private final long relationId;

    public Sheet(String name, int sheetId, long relationId) {
        this.name = name;
        this.sheetId = sheetId;
        this.relationId = relationId;
    }

    public String getName() {
        return name;
    }

    public int getSheetId() {
        return sheetId;
    }

    public long getRelationId() {
        return relationId;
    }
}
