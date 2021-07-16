package net.spacive.xlsx.context;

import net.spacive.xlsx.content.IContentFile;
import net.spacive.xlsx.content.ISourceFile;
import net.spacive.xlsx.core_config.CoreConfig;
import net.spacive.xlsx.rels.Relationship;
import net.spacive.xlsx.rels.RelationshipsHolder;
import net.spacive.xlsx.util.CloseableStringConsumer;
import net.spacive.xml_core.IStringConsumer;
import net.spacive.xml_core.OutputStreamStringConsumer;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Holds state of xlsx document creation process.
 */
public class XlsxContext {

    private static class OpenedEntry {
        private final ZipEntry zipEntry;
        private final CloseableStringConsumer consumer;

        public OpenedEntry(ZipEntry zipEntry, CloseableStringConsumer consumer) {
            this.zipEntry = zipEntry;
            this.consumer = consumer;
        }
    }

    private final DateFormat xlsxDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

    private final OutputStream xlsxOutputStream;
    private final CoreConfig coreConfig;

    private final ZipOutputStream zipOutputStream;
    private final OutputStreamStringConsumer streamStringConsumer;

    private final RelationshipsHolder rootRelations = new RelationshipsHolder();
    private final RelationshipsHolder workbookRelations = new RelationshipsHolder();

    // used to generate [Content_Types].xml file
    private final List<IContentFile> contentFiles = new ArrayList<>();
    private final List<ISourceFile> sourceFiles = new ArrayList<>();

    private final String styles;
    private final String theme;

    private OpenedEntry openedEntry = null;

    public XlsxContext(OutputStream xlsxOutputStream,
                        CoreConfig coreConfig,
                        String styles,
                        String theme) {

        this.xlsxOutputStream = xlsxOutputStream;
        this.coreConfig = coreConfig;
        this.styles = styles;
        this.theme = theme;

        zipOutputStream = new ZipOutputStream(xlsxOutputStream);
        streamStringConsumer = new OutputStreamStringConsumer(zipOutputStream);
    }

    public String getStyles() {
        return styles;
    }

    public String getTheme() {
        return theme;
    }

    public String formatDate(Date date) {
        return xlsxDateFormat.format(date);
    }

    public CoreConfig getCoreConfigData() {
        return coreConfig;
    }

    public IStringConsumer openEntry(String entryName) throws IOException {
        if (openedEntry != null) {
            throw new RuntimeException("previously opened entry must be closed before opening new entry");
        }

        openedEntry = new OpenedEntry(new ZipEntry(entryName), new CloseableStringConsumer(streamStringConsumer));
        zipOutputStream.putNextEntry(openedEntry.zipEntry);

        return openedEntry.consumer;
    }

    public void closeEntry() throws IOException {
        if (openedEntry == null) {
            throw new RuntimeException("cannot close entry because no entry is opened");
        }

        if (openedEntry.consumer.isClosed()) {
            throw new RuntimeException("cannot close already closed entry");
        }

        openedEntry.consumer.close();
        openedEntry = null;
        streamStringConsumer.flush();
        zipOutputStream.closeEntry();
    }

    public void close() throws IOException {
        streamStringConsumer.flush();
        zipOutputStream.close();
        xlsxOutputStream.close();
    }

    public void addRootRelation(Relationship relationship) {
        rootRelations.addRelationship(relationship);
    }

    public void addWorkbookRelation(Relationship relationship) {
        workbookRelations.addRelationship(relationship);
    }

    public void registerContentFile(IContentFile contentFile) {
        this.contentFiles.add(contentFile);
    }

    public void registerSourceFile(ISourceFile sourceFile) {
        this.sourceFiles.add(sourceFile);
    }

    public List<IContentFile> getContentFiles() {
        return contentFiles;
    }

    public RelationshipsHolder getRootRelations() {
        return rootRelations;
    }

    public RelationshipsHolder getWorkbookRelations() {
        return workbookRelations;
    }

    public List<ISourceFile> getSourceFiles() {
        return sourceFiles;
    }

    public static String getExcelColumnName(int columnNumber) {
        StringBuilder builder = new StringBuilder();

        int dividend = columnNumber;
        int modulo;

        while (dividend > 0) {
            modulo = (dividend - 1) % 26;
            builder.append((char) (65 + modulo));
            dividend = (int) ((dividend - modulo) / 26);
        }

        return builder.reverse().toString();
    }
}
