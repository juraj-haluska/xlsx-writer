package net.spacive.xlsx;

import net.spacive.xlsx.context.XlsxContext;
import net.spacive.xlsx.core_config.CoreConfig;

import java.io.OutputStream;
import java.util.Date;

public class XlsxDocumentBuilder {

    private final OutputStream outputStream;
    private final CoreConfig coreConfig;

    private String styles = "";
    private String theme = "";

    public XlsxDocumentBuilder(OutputStream outputStream) {
        this.outputStream = outputStream;

        this.coreConfig = new CoreConfig();
    }

    public XlsxDocument build() {
        XlsxContext xlsxContext = new XlsxContext(outputStream, coreConfig, styles, theme);
        XlsxDocument document = new XlsxDocument(xlsxContext);

        return document;
    }

    public XlsxDocumentBuilder setStyles(String styles) {
        this.styles = styles;
        return this;
    }

    public XlsxDocumentBuilder setTheme(String theme) {
        this.theme = theme;
        return this;
    }

    public void setCreatedAt(Date date) {
        coreConfig.setCreated(date);
    }

    public void setCreator(String creator) {
        coreConfig.setCreator(creator);
    }

    public void setLastModifiedAt(Date date) {
        coreConfig.setLastModified(date);
    }

    public void setLastModifiedBy(String modifiedBy) {
        coreConfig.setLastModifiedBy(modifiedBy);
    }
}
