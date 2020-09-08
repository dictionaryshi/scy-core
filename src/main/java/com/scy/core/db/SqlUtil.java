package com.scy.core.db;

import com.scy.core.StringUtil;
import com.scy.core.SystemUtil;

import java.lang.reflect.Field;
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

    public static <T> String batchInsert(Class<T> clazz, String table) {
        StringBuilder sb = new StringBuilder();
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
            sb.append("#{").append("entry.").append(fieldName).append("}").append(StringUtil.COMMA).append(SystemUtil.SYSTEM_LINE_BREAK);
            sb.append("</if>").append(SystemUtil.SYSTEM_LINE_BREAK);
        });
        sb.append("</trim>").append(SystemUtil.SYSTEM_LINE_BREAK);
        sb.append("</foreach>").append(SystemUtil.SYSTEM_LINE_BREAK);
        sb.append("</script>").append(SystemUtil.SYSTEM_LINE_BREAK);
        return sb.toString();
    }

    public static <T> String batchUpdate(Class<T> clazz, String table) {
        StringBuilder sb = new StringBuilder();
        Field[] declaredFields = clazz.getDeclaredFields();
        sb.append("<script>").append(SystemUtil.SYSTEM_LINE_BREAK);
        sb.append("<foreach collection=\"list\" item=\"entry\" separator=\";\">").append(SystemUtil.SYSTEM_LINE_BREAK);
        sb.append("update ").append(table).append(StringUtil.SPACE).append(SystemUtil.SYSTEM_LINE_BREAK);
        sb.append("<set>").append(SystemUtil.SYSTEM_LINE_BREAK);
        Stream.of(declaredFields).map(Field::getName).forEach(fieldName -> {
            sb.append("<if test=\"entry.").append(fieldName).append(" != null\">").append(SystemUtil.SYSTEM_LINE_BREAK);
            sb.append("#{").append("entry.").append(fieldName).append("}").append(StringUtil.COMMA).append(SystemUtil.SYSTEM_LINE_BREAK);
            sb.append("</if>").append(SystemUtil.SYSTEM_LINE_BREAK);
        });
        sb.append("</set>").append(SystemUtil.SYSTEM_LINE_BREAK);
        sb.append("where id = #{id}").append(SystemUtil.SYSTEM_LINE_BREAK);
        sb.append("</foreach>").append(SystemUtil.SYSTEM_LINE_BREAK);
        sb.append("</script>").append(SystemUtil.SYSTEM_LINE_BREAK);
        return sb.toString();
    }
}
