package net.spacive.xlsx;

import net.spacive.xlsx.worksheet.RowParams;
import net.spacive.xlsx.worksheet.WorkSheet;
import net.spacive.xlsx.worksheet.WorkSheetParams;
import net.spacive.xlsx.worksheet.cells.Cells;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XlsxRenderTest {

    @Test
    public void renderDocument() throws IOException, URISyntaxException {
        File outputFile = new File("out.xlsx");
        OutputStream outputStream = new FileOutputStream(outputFile);

        URL url = getClass().getClassLoader().getResource("styles.xml");
        byte [] stylesBytes = Files.readAllBytes(Paths.get(url.toURI()));
        String styles = new String(stylesBytes);

        XlsxDocument document = new XlsxDocumentBuilder(outputStream)
                .setStyles(styles)
                .build();

        WorkSheetParams params = new WorkSheetParams.Builder()
                .setDefaultRowHeight(12.8)
                .build();

        WorkSheet sheet = document.openWorksheet("sheet1", params);

        sheet.openRow(
                RowParams.at(1))
                .putCell(1, Cells.string("hello", 1))
                .putCell(2, Cells.string("world", 2))
                .close();

        sheet.close();
        document.close();

        System.out.print(outputFile.getAbsolutePath());
    }
}
