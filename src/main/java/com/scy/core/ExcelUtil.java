package com.scy.core;

import com.google.common.collect.Lists;
import com.scy.core.format.MessageUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;
import java.util.List;

/**
 * ExcelUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/31.
 */
@Slf4j
public class ExcelUtil {

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
}
