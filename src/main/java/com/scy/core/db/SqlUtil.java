package com.scy.core.db;

import com.scy.core.CollectionUtil;
import com.scy.core.StringUtil;
import com.scy.core.SystemUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

/**
 * SqlUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/7.
 */
public class SqlUtil {

    public static <T> String batchInsert(List<T> datas, String table) {
        if (CollectionUtil.isEmpty(datas)) {
            return StringUtil.EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        T object = CollectionUtil.firstElement(datas);
        Class<?> clazz = object.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        sb.append("<script>").append(SystemUtil.SYSTEM_LINE_BREAK);
        sb.append("<foreach collection=\"list\" item=\"entry\" separator=\";\">").append(SystemUtil.SYSTEM_LINE_BREAK);
        sb.append("insert into ").append(table).append(StringUtil.SPACE).append(SystemUtil.SYSTEM_LINE_BREAK);
        sb.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">").append(SystemUtil.SYSTEM_LINE_BREAK);
        Stream.of(declaredFields).map(Field::getName).forEach(fieldName -> {
            sb.append("<if test=\"entry.").append(fieldName).append(" != null\">").append(SystemUtil.SYSTEM_LINE_BREAK);
            sb.append(StringUtil.humpToLine(fieldName)).append(StringUtil.COMMA).append(SystemUtil.SYSTEM_LINE_BREAK);
            sb.append("</if>").append(SystemUtil.SYSTEM_LINE_BREAK);
        });
        sb.append("</trim>").append(SystemUtil.SYSTEM_LINE_BREAK);
        sb.append("<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">").append(SystemUtil.SYSTEM_LINE_BREAK);
        Stream.of(declaredFields).map(Field::getName).forEach(fieldName -> {
            sb.append("<if test=\"entry.").append(fieldName).append(" != null\">").append(SystemUtil.SYSTEM_LINE_BREAK);
            sb.append("#{").append(fieldName).append("}").append(StringUtil.COMMA).append(SystemUtil.SYSTEM_LINE_BREAK);
            sb.append("</if>").append(SystemUtil.SYSTEM_LINE_BREAK);
        });
        sb.append("</trim>").append(SystemUtil.SYSTEM_LINE_BREAK);
        sb.append("</foreach>").append(SystemUtil.SYSTEM_LINE_BREAK);
        sb.append("</script>").append(SystemUtil.SYSTEM_LINE_BREAK);
        return sb.toString();
    }
}
