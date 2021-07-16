package net.spacive.xlsx;

import net.spacive.xlsx.content.IContentFile;
import net.spacive.xlsx.content.ISourceFile;
import net.spacive.xlsx.content.impl.ContentFileFactory;
import net.spacive.xlsx.content.impl.XmlSourceFile;
import net.spacive.xlsx.context.XlsxContext;
import net.spacive.xlsx.core_config.CoreConfigRenderable;
import net.spacive.xlsx.rels.Relationships;
import net.spacive.xlsx.renderable.ContentTypesRenderable;
import net.spacive.xlsx.renderable.RelsRenderable;
import net.spacive.xlsx.renderable.WorkBookRenderable;
import net.spacive.xlsx.worksheet.WorkSheet;
import net.spacive.xlsx.worksheet.WorkSheetParams;
import net.spacive.xml_core.IStringConsumer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XlsxDocument {

    private final XlsxContext xlsxContext;
    private final List<Sheet> sheets = new ArrayList<>();
    private int worksheetIdCounter = 0;

    // worker thread
    XlsxDocument(XlsxContext xlsxContext) {
        this.xlsxContext = xlsxContext;
    }

    public void close() throws IOException {
        processStyles();
        processTheme();
        //processCore();
        processWorkbook();
        processRelations();
        processContentTypes();

        writeSourceFiles();

        xlsxContext.close();
    }

    private void processStyles() {
        String styles = xlsxContext.getStyles();

        if (!styles.isEmpty()) {
            IContentFile stylesContentFile = ContentFileFactory.styles(styles);
            xlsxContext.addWorkbookRelation(Relationships.styles());
            xlsxContext.registerContentFile(stylesContentFile);
            xlsxContext.registerSourceFile(stylesContentFile);
        }
    }

    private void processTheme() {
        String theme = xlsxContext.getTheme();

        if (!theme.isEmpty()) {
            IContentFile themeContentFile = ContentFileFactory.theme(theme, 1);
            xlsxContext.addWorkbookRelation(Relationships.theme(1));
            xlsxContext.registerContentFile(themeContentFile);
            xlsxContext.registerSourceFile(themeContentFile);
        }
    }

    private void processCore() {
        CoreConfigRenderable coreConfigRenderable = new CoreConfigRenderable(xlsxContext.getCoreConfigData(), xlsxContext);
        IContentFile coreContentFile = ContentFileFactory.core(coreConfigRenderable);

        xlsxContext.addRootRelation(Relationships.coreProperties());
        xlsxContext.registerContentFile(coreContentFile);
        xlsxContext.registerSourceFile(coreContentFile);
    }

    private void processWorkbook() {
        xlsxContext.addRootRelation(Relationships.workbook());
        WorkBookRenderable workbookRenderable = new WorkBookRenderable(sheets);
        IContentFile workbookFile = ContentFileFactory.workbook(workbookRenderable);
        xlsxContext.registerSourceFile(workbookFile);
        xlsxContext.registerContentFile(workbookFile);
    }

    private void processRelations() {
        IRenderable rootRelsRenderable = new RelsRenderable(xlsxContext.getRootRelations());
        ISourceFile rootRelsFile = new XmlSourceFile("_rels/.rels", rootRelsRenderable);
        xlsxContext.registerSourceFile(rootRelsFile);

        IRenderable workbookRelsRenderable = new RelsRenderable(xlsxContext.getWorkbookRelations());
        ISourceFile workbookRelsFile = new XmlSourceFile("xl/_rels/workbook.xml.rels", workbookRelsRenderable);
        xlsxContext.registerSourceFile(workbookRelsFile);
    }

    private void processContentTypes() {
        IRenderable contentTypesRenderable = new ContentTypesRenderable(xlsxContext.getContentFiles());
        ISourceFile contentTypesFile = new XmlSourceFile("[Content_Types].xml", contentTypesRenderable);
        xlsxContext.registerSourceFile(contentTypesFile);
    }

    private void writeSourceFiles() throws IOException {
        for (ISourceFile sourceFile: xlsxContext.getSourceFiles()) {
            IStringConsumer consumer = xlsxContext.openEntry(sourceFile.getEntryName());
            sourceFile.renderInto(consumer);
            xlsxContext.closeEntry();
        }
    }

    public WorkSheet openWorksheet(String sheetName) throws IOException {
        return openWorksheet(sheetName, WorkSheetParams.EMPTY);
    }

    public WorkSheet openWorksheet(String sheetName, WorkSheetParams workSheetParams) throws IOException {
        int worksheetId = ++worksheetIdCounter;
        long relId = xlsxContext.getWorkbookRelations().addRelationship(Relationships.worksheet(worksheetId));
        IContentFile contentFile = ContentFileFactory.worksheet(worksheetId);
        xlsxContext.registerContentFile(contentFile);
        sheets.add(new Sheet(sheetName, worksheetId, relId));

        IStringConsumer consumer = xlsxContext.openEntry(contentFile.getEntryName());

        return new WorkSheet(consumer, xlsxContext, worksheetId, workSheetParams);
    }
}
