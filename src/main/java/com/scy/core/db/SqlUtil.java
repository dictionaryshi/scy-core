package com.scy.core.db;

import com.scy.core.CollectionUtil;
import com.scy.core.StringUtil;
import com.scy.core.SystemUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SqlUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/8.
 */
public class SqlUtil {

    private SqlUtil() {
    }

    public static <T> String batchInsert(Class<T> clazz, String table, List<String> excludeFields) {
        StringBuilder sb = new StringBuilder();
        Field[] declaredFields = clazz.getDeclaredFields();
        List<String> fieldNames = Stream.of(declaredFields).map(Field::getName)
                .filter(fieldName -> !CollectionUtil.emptyIfNull(excludeFields).contains(fieldName)).collect(Collectors.toList());

        sb.append("<script>").append(SystemUtil.SYSTEM_LINE_BREAK);
        sb.append("insert into ").append(table).append(StringUtil.SPACE).append(SystemUtil.SYSTEM_LINE_BREAK);
        sb.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">").append(SystemUtil.SYSTEM_LINE_BREAK);
        fieldNames.forEach(
                fieldName -> sb.append(StringUtil.humpToLine(fieldName)).append(StringUtil.COMMA).append(SystemUtil.SYSTEM_LINE_BREAK)
        );
        sb.append("</trim>").append(SystemUtil.SYSTEM_LINE_BREAK);

        sb.append(" VALUES ").append(SystemUtil.SYSTEM_LINE_BREAK);

        sb.append("<foreach collection=\"list\" item=\"entry\" separator=\",\">").append(SystemUtil.SYSTEM_LINE_BREAK);
        sb.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">").append(SystemUtil.SYSTEM_LINE_BREAK);
        fieldNames.forEach(
                fieldName -> sb.append("#{").append("entry.").append(fieldName).append("}").append(StringUtil.COMMA).append(SystemUtil.SYSTEM_LINE_BREAK)
        );
        sb.append("</trim>").append(SystemUtil.SYSTEM_LINE_BREAK);
        sb.append("</foreach>").append(SystemUtil.SYSTEM_LINE_BREAK);
        sb.append("</script>").append(SystemUtil.SYSTEM_LINE_BREAK);
        return sb.toString();
    }
}
