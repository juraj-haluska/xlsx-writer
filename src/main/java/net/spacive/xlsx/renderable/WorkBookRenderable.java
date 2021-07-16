package net.spacive.xlsx.renderable;

import net.spacive.xlsx.IRenderable;
import net.spacive.xlsx.Sheet;
import net.spacive.xlsx.util.XmlAttributes;
import net.spacive.xml_core.IStringConsumer;
import net.spacive.xml_core.Node;
import net.spacive.xml_core.XmlAttribute;
import net.spacive.xml_core.XmlElement;

import java.util.List;

public class WorkBookRenderable implements IRenderable {

    private final List<Sheet> sheets;

    public WorkBookRenderable(List<Sheet> sheets) {
        this.sheets = sheets;
    }

    @Override
    public void renderInto(IStringConsumer consumer) {
        Node rootElement = new XmlElement(consumer, "workbook")
                .addAttribute(XmlAttributes.namespace("http://schemas.openxmlformats.org/spreadsheetml/2006/main"))
                .addAttribute(new XmlAttribute("xmlns:r", "http://schemas.openxmlformats.org/officeDocument/2006/relationships"))
                .addAttribute(new XmlAttribute("xmlns:mc", "http://schemas.openxmlformats.org/markup-compatibility/2006"))
                .addAttribute(new XmlAttribute("mc:Ignorable", "x15 xr xr6 xr10 xr2"))
                .addAttribute(new XmlAttribute("xmlns:x15", "http://schemas.microsoft.com/office/spreadsheetml/2010/11/main"))
                .addAttribute(new XmlAttribute("xmlns:xr", "http://schemas.microsoft.com/office/spreadsheetml/2014/revision"))
                .addAttribute(new XmlAttribute("xmlns:xr6", "http://schemas.microsoft.com/office/spreadsheetml/2016/revision6"))
                .addAttribute(new XmlAttribute("xmlns:xr10", "http://schemas.microsoft.com/office/spreadsheetml/2016/revision10"))
                .addAttribute(new XmlAttribute("xmlns:xr2", "http://schemas.microsoft.com/office/spreadsheetml/2015/revision2"))
                .open();

        Node sheets = new XmlElement(rootElement, "sheets")
                .open();

        for (Sheet sheet: this.sheets) {
            new XmlElement(sheets, "sheet")
                    .addAttribute(new XmlAttribute("name", sheet.getName()))
                    .addAttribute(new XmlAttribute("sheetId", String.valueOf(sheet.getSheetId())))
                    .addAttribute(new XmlAttribute("r:id", "rId" + sheet.getRelationId()))
                    .open().close();
        }

        sheets.close();
        rootElement.close();
    }
}
