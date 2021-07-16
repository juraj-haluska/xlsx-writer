package net.spacive.xlsx;

import net.spacive.xlsx.worksheet.Row;
import net.spacive.xlsx.worksheet.WorkSheet;
import net.spacive.xlsx.worksheet.cells.Cells;
import org.junit.jupiter.api.Test;

import java.io.*;

public class XlsxRenderTest {

    @Test
    public void renderDocument() throws IOException {
        File outputFile = new File("out.xlsx");
        OutputStream outputStream = new FileOutputStream(outputFile);

        XlsxDocument document = new XlsxDocumentBuilder(outputStream)
                .build();

        WorkSheet sheet = document.createWorksheet("sheet1");
        sheet.open();

        sheet.openRow(Row.atIndex(1));
        sheet.putCell(1, Cells.string("hello"));
        sheet.putCell(2, Cells.string("world"));
        sheet.closeRow();

        sheet.close();
        document.close();

        System.out.print(outputFile.getAbsolutePath());
    }
}
