package net.spacive.xlsx.rels;

public class Relationships {

    public static Relationship workbook() {
        return new Relationship(
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument",
                "xl/workbook.xml",
                false);
    }

    public static Relationship hyperlink(String uri) {
        return new Relationship(
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/hyperlink",
                uri,
                true);
    }

    public static Relationship coreProperties() {
        return new Relationship(
                "http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties",
                "docProps/core.xml",
                false);
    }

    public static Relationship sharedStrings() {
        return new Relationship(
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/sharedStrings",
                "docProps/core.xml",
                false);
    }

    public static Relationship styles() {
        return new Relationship(
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles",
                "styles.xml",
                false);
    }

    public static Relationship theme(int themeId) {
        return new Relationship(
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/theme",
                "theme/theme" + themeId + ".xml",
                false);
    }

    public static Relationship worksheet(int worksheetId) {
        return new Relationship(
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet",
                "worksheets/sheet" + worksheetId + ".xml",
                false);
    }
}
