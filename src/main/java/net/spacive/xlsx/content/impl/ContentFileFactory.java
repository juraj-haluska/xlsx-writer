package net.spacive.xlsx.content.impl;

import net.spacive.xlsx.IRenderable;
import net.spacive.xlsx.content.ContentType;
import net.spacive.xlsx.content.IContentFile;
import net.spacive.xlsx.core_config.CoreConfigRenderable;
import net.spacive.xlsx.renderable.WorkBookRenderable;

public class ContentFileFactory {

    public static IContentFile styles(String styleData) {
        return new StringContentFile(ContentType.STYLES, "xl/styles.xml", styleData);
    }

    public static IContentFile theme(String themeData, int themeId) {
        return new StringContentFile(ContentType.THEME, "xl/theme/theme" + themeId + ".xml", themeData);
    }

    public static IContentFile core(CoreConfigRenderable coreConfigRenderable) {
        return new XmlContentFile<>(ContentType.CORE_PROPERTIES, "docProps/core.xml", coreConfigRenderable);
    }

    public static IContentFile workbook(WorkBookRenderable workBookRenderable) {
        return new XmlContentFile<>(ContentType.WORKBOOK, "xl/workbook.xml", workBookRenderable);
    }

    public static IContentFile worksheet(int sheetId) {
        return new XmlContentFile<>(ContentType.WORKSHEET, "xl/worksheets/sheet" + sheetId + ".xml", IRenderable.EMPTY);
    }
}
