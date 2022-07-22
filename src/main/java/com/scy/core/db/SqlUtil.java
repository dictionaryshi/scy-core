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

        sb.append("<script>");
        sb.append("insert into ").append(table);
        sb.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        fieldNames.forEach(
                fieldName -> sb.append(StringUtil.humpToLine(fieldName)).append(StringUtil.COMMA)
        );
        sb.append("</trim>");

        sb.append("VALUES");

        sb.append("<foreach collection=\"list\" item=\"entry\" separator=\",\">");
        sb.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        fieldNames.forEach(
                fieldName -> sb.append("#{").append("entry.").append(fieldName).append("}").append(StringUtil.COMMA)
        );
        sb.append("</trim>");
        sb.append("</foreach>");
        sb.append("</script>");
        return sb.toString();
    }

    public static <T> String batchUpdate(Class<T> clazz, String table, List<String> includeFields) {
        StringBuilder sb = new StringBuilder();
        Field[] declaredFields = clazz.getDeclaredFields();
        List<String> fieldNames = Stream.of(declaredFields).map(Field::getName)
                .filter(fieldName -> CollectionUtil.emptyIfNull(includeFields).contains(fieldName)).collect(Collectors.toList());

        sb.append("<script>");
        sb.append("update ").append(table);
        sb.append(" set ");
        sb.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        fieldNames.forEach(
                fieldName -> sb.append(StringUtil.humpToLine(fieldName))
                        .append(" = ")
                        .append("<foreach collection=\"list\" item=\"entry\" separator=\" \" open=\"case id\" close=\"end\">")
                        .append("when #{entry.id} then #{entry.").append(fieldName).append("}")
                        .append("</foreach>")
                        .append(StringUtil.COMMA)
        );
        sb.append("</trim>");

        sb.append(" where id in ");
        sb.append("<foreach collection=\"list\" item=\"entry\" separator=\",\" open=\"(\" close=\")\">");
        sb.append("#{entry.id}");
        sb.append("</foreach>");
        sb.append("</script>");
        return sb.toString();
    }
}
