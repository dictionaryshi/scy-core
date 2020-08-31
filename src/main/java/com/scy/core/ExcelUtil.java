package com.scy.core;

import com.google.common.collect.Lists;
import com.scy.core.format.MessageUtil;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * ExcelUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/31.
 */
@Slf4j
public class ExcelUtil {

    private ExcelUtil() {
    }

    public static void createExcel(
            OutputStream out,
            String sheetName,
            List<String> heads,
            List<List<?>> datas,
            int limit
    ) {
        WritableWorkbook writableWorkbook = null;
        try {
            writableWorkbook = Workbook.createWorkbook(out);

            List<List<List<?>>> pages = Lists.partition(datas, limit);
            for (int page = 0; page < pages.size(); page++) {
                WritableSheet pageSheet = writableWorkbook.createSheet(sheetName + (page + 1), page);

                for (int head = 0; head < heads.size(); head++) {
                    Label label = new Label(head, 0, heads.get(head));
                    pageSheet.addCell(label);
                }

                List<List<?>> pageRows = pages.get(page);
                for (int pageRow = 0; pageRow < pageRows.size(); pageRow++) {
                    List<?> pageRowColumns = pageRows.get(pageRow);
                    for (int pageRowColumn = 0; pageRowColumn < pageRowColumns.size(); pageRowColumn++) {
                        String pageRowColumnData = ObjectUtil.obj2Str(pageRowColumns.get(pageRowColumn));
                        Label label = new Label(pageRowColumn, pageRow + 1, pageRowColumnData);
                        pageSheet.addCell(label);
                    }
                }
            }
            writableWorkbook.write();
        } catch (Throwable e) {
            log.error(MessageUtil.format("createExcel error", e, "sheetName", sheetName, "heads", heads, "datas", datas));
        } finally {
            if (writableWorkbook != null) {
                try {
                    writableWorkbook.close();
                } catch (Throwable e) {
                    log.error(MessageUtil.format("createExcel close error", e));
                }
            }
        }
    }

    public static List<List<String>> readExcel(InputStream in, boolean ignoreHeader) {
        List<List<String>> rows = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(in);

            Sheet[] sheets = workbook.getSheets();
            for (Sheet sheet : sheets) {
                int start = 0;
                if (ignoreHeader) {
                    start = 1;
                }

                for (int sheetRow = start; sheetRow < sheet.getRows(); sheetRow++) {
                    List<String> columns = new ArrayList<>();
                    for (int sheetColumn = 0; sheetColumn < sheet.getColumns(); sheetColumn++) {
                        Cell cell = sheet.getCell(sheetColumn, sheetRow);
                        String content = cell.getContents();
                        columns.add(content == null ? StringUtil.EMPTY : content.trim());
                    }
                    if (!CollectionUtil.isEmpty(columns)) {
                        rows.add(columns);
                    }
                }
            }
        } catch (Exception e) {
            log.error(MessageUtil.format("readExcel error", e));
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }

        return rows;
    }
}
