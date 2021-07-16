package net.spacive.xlsx.content;

public enum  ContentType {
    NONE(""),
    RELATIONSHIPS("application/vnd.openxmlformats-package.relationships+xml"),
    XML("application/xml"),
    THEME("application/vnd.openxmlformats-officedocument.theme+xml"),
    STYLES("application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml"),
    SHARED_STRINGS("application/vnd.openxmlformats-officedocument.spreadsheetml.sharedStrings+xml"),
    CORE_PROPERTIES("application/vnd.openxmlformats-package.core-properties+xml"),
    WORKBOOK("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml"),
    WORKSHEET("application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml");

    private final String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return contentType;
    }
}
