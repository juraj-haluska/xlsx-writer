package net.spacive.xlsx.worksheet;

import net.spacive.xlsx.content.impl.XmlSourceFile;
import net.spacive.xlsx.context.XlsxContext;
import net.spacive.xlsx.rels.Relationships;
import net.spacive.xlsx.rels.RelationshipsHolder;
import net.spacive.xlsx.renderable.RelsRenderable;
import net.spacive.xlsx.util.XmlAttributes;
import net.spacive.xml_core.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WorkSheet {

    private static class Link {
        private final int rowId;
        private final int columnId;
        private final long relationId;

        public Link(int rowId, int columnId, long relationId) {
            this.rowId = rowId;
            this.columnId = columnId;
            this.relationId = relationId;
        }
    }

    private final IStringConsumer consumer;
    private final XlsxContext xlsxContext;
    private final WorkSheetParams workSheetParams;

    private final int sheetId;

    private final RelationshipsHolder linksRelations = new RelationshipsHolder();

    private final List<CellRange> mergedCellsList = new ArrayList<>();

    private final List<Link> links = new ArrayList<>();

    private Node document;
    private Node rootElement;
    private Node sheetData;

    private boolean isOpen = false;

    private Row openedRow = null;

    public WorkSheet(IStringConsumer consumer,
                     XlsxContext xlsxContext,
                     int sheetId,
                     WorkSheetParams workSheetParams) {

        this.consumer = consumer;
        this.xlsxContext = xlsxContext;
        this.sheetId = sheetId;
        this.workSheetParams = workSheetParams;
    }

    private void open() {
        document = new XmlDocument(consumer)
                .setDeclaration("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
                .open();

        rootElement = new XmlElement(document, "worksheet")
                .addAttribute(XmlAttributes.namespace("http://schemas.openxmlformats.org/spreadsheetml/2006/main"))
                .addAttribute(new XmlAttribute("xmlns:r", "http://schemas.openxmlformats.org/officeDocument/2006/relationships"))
                .addAttribute(new XmlAttribute("xmlns:mc", "http://schemas.openxmlformats.org/markup-compatibility/2006"))
                .addAttribute(new XmlAttribute("mc:Ignorable", "x14ac xr xr2 xr3"))
                .addAttribute(new XmlAttribute("xmlns:x14ac", "http://schemas.microsoft.com/office/spreadsheetml/2009/9/ac"))
                .addAttribute(new XmlAttribute("xmlns:xr", "http://schemas.microsoft.com/office/spreadsheetml/2014/revision"))
                .addAttribute(new XmlAttribute("xmlns:xr2", "http://schemas.microsoft.com/office/spreadsheetml/2015/revision2"))
                .addAttribute(new XmlAttribute("xmlns:xr3", "http://schemas.microsoft.com/office/spreadsheetml/2016/revision3"))
                .open();

        processWorkSheetParams();

        sheetData = new XmlElement(rootElement, "sheetData")
                .open();
    }

    private void openIfNotOpened() {
        if (!this.isOpen) {
            open();
            this.isOpen = true;
        }
    }

    public void close() throws IOException {
        openIfNotOpened();
        sheetData.close();

        processMergedCells();
        processLinks();

        rootElement.close();
        document.close();
        xlsxContext.closeEntry();

        processLinkRels();
    }

    public Row openRow(RowParams rowParams) {
        openIfNotOpened();

        if (openedRow != null && !openedRow.isClosed()) {
            throw new RuntimeException("previous row must be closed before new row can be added");
        }

        XmlElement rowElement = new XmlElement(sheetData, "row")
                .addAttribute(new XmlAttribute("r", String.valueOf(rowParams.getRowNumber())));

        if (rowParams.getSpan() != RowParams.NO_SPAN) {
            rowElement.addAttribute(new XmlAttribute("spans", String.format(Locale.US, "%d:%d",
                    rowParams.getSpan().getStart(),
                    rowParams.getSpan().getEnd())));
        }

        if (rowParams.getHeight() != RowParams.DEFAULT_HEIGHT) {
            rowElement.addAttribute(new XmlAttribute("ht", String.valueOf(rowParams.getHeight())));
        }

        if (rowParams.isThickBot()) {
            rowElement.addAttribute(new XmlAttribute("thickBot", "1"));
        }

        if (rowParams.isCustomHeight()) {
            rowElement.addAttribute(new XmlAttribute("customHeight", "1"));
        }

        if (rowParams.getDyDescent() != RowParams.NO_DY_DESCENT) {
            rowElement.addAttribute(new XmlAttribute("x14ac:dyDescent", String.valueOf(rowParams.getDyDescent())));
        }

        openedRow = new Row(rowParams, rowElement.open(), this);
        return openedRow;
    }

    void registerMergedCells(CellRange cellRange) {
        mergedCellsList.add(cellRange);
    }

    void registerHyperlink(String hyperLink, int row, int column) {
        long relationId = linksRelations.addRelationship(Relationships.hyperlink(hyperLink));
        links.add(new WorkSheet.Link(row, column, relationId));
    }

    private String getLinksEntryName() {
        return "xl/worksheets/_rels/sheet" + sheetId + ".xml.rels";
    }

    private void processWorkSheetParams() {
        if (workSheetParams != WorkSheetParams.EMPTY) {
            processSheetFormatPr();
            processColumnConfigs();
        }
    }

    private void processSheetFormatPr() {
        XmlElement xmlElement = new XmlElement(rootElement, "sheetFormatPr");

        if (workSheetParams.getDefaultRowHeight() > 0) {
            xmlElement.addAttribute(new XmlAttribute("defaultRowHeight", String.valueOf(workSheetParams.getDefaultRowHeight())));
        }

        if (workSheetParams.getDyDescent() > 0) {
            xmlElement.addAttribute(new XmlAttribute("x14ac:dyDescent", String.valueOf(workSheetParams.getDyDescent())));
        }

        xmlElement.open().close();
    }

    private void processColumnConfigs() {
        if (workSheetParams.getColumnConfigList().size() > 0) {
            Node colsElement = new XmlElement(rootElement, "cols")
                    .open();

            for (WorkSheetParams.ColumnConfig columnConfig : workSheetParams.getColumnConfigList()) {
                XmlElement colConfigElement = new XmlElement(colsElement, "col");

                if (columnConfig.getMin() > 0) {
                    colConfigElement.addAttribute(new XmlAttribute("min", String.valueOf(columnConfig.getMin())));
                    colConfigElement.addAttribute(new XmlAttribute("customWidth", "1"));
                }
                if (columnConfig.getMax() > 0) {
                    colConfigElement.addAttribute(new XmlAttribute("max", String.valueOf(columnConfig.getMax())));
                }
                if (columnConfig.getWidth() > 0) {
                    colConfigElement.addAttribute(new XmlAttribute("width", String.valueOf(columnConfig.getWidth())));
                }

                colConfigElement.open().close();
            }

            colsElement.close();
        }
    }

    private void processLinkRels() {
        RelsRenderable relsRenderable = new RelsRenderable(linksRelations);
        XmlSourceFile xmlSourceFile = new XmlSourceFile(getLinksEntryName(), relsRenderable);
        xlsxContext.registerSourceFile(xmlSourceFile);
    }

    private void processLinks() {
        if (links.size() > 0) {
            Node linksElement = new XmlElement(rootElement, "hyperlinks")
                    .open();

            for (Link link: links) {
                String cell = String.format(Locale.US, "%s%d", XlsxContext.getExcelColumnName(link.columnId), link.rowId);
                new XmlElement(linksElement, "hyperlink")
                        .addAttribute(new XmlAttribute("ref", cell))
                        .addAttribute(new XmlAttribute("r:id", String.format(Locale.US, "rId%d", link.relationId)))
                        .open().close();
            }

            linksElement.close();
        }
    }

    private void processMergedCells() {
        if (!mergedCellsList.isEmpty()) {
            Node mergedCells = new XmlElement(rootElement, "mergeCells")
                    .addAttribute(new XmlAttribute("count", Integer.toString(mergedCellsList.size())))
                    .open();

            for (CellRange cellRange : mergedCellsList) {
                new XmlElement(mergedCells, "mergeCell")
                        .addAttribute(new XmlAttribute("ref", cellRange.renderRange()))
                        .open().close();
            }

            mergedCells.close();
        }
    }
}
