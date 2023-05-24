package com.scy.core.db;

import com.scy.core.StringUtil;

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

    public static <T> String batchInsert(Class<T> clazz, String table) {
        StringBuilder sb = new StringBuilder();
        Field[] declaredFields = clazz.getDeclaredFields();
        List<String> fieldNames = Stream.of(declaredFields).map(Field::getName).collect(Collectors.toList());

        sb.append("<script>");
        sb.append("insert into ").append(table);
        sb.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        fieldNames.forEach(
                fieldName -> {
                    sb.append("<if test=\"rows[0].").append(fieldName).append(" != null\">");
                    sb.append(StringUtil.humpToLine(fieldName)).append(StringUtil.COMMA);
                    sb.append("</if>");
                }
        );
        sb.append("</trim>");

        sb.append("VALUES");

        sb.append("<foreach collection=\"list\" item=\"entry\" separator=\",\">");
        sb.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        fieldNames.forEach(
                fieldName -> {
                    sb.append("<if test=\"rows[0].").append(fieldName).append(" != null\">");
                    sb.append("#{").append("entry.").append(fieldName).append("}").append(StringUtil.COMMA);
                    sb.append("</if>");
                }
        );
        sb.append("</trim>");
        sb.append("</foreach>");
        sb.append("</script>");
        return sb.toString();
    }

    public static <T> String batchUpdate(Class<T> clazz, String table) {
        StringBuilder sb = new StringBuilder();
        Field[] declaredFields = clazz.getDeclaredFields();
        List<String> fieldNames = Stream.of(declaredFields).map(Field::getName).collect(Collectors.toList());

        sb.append("<script>");
        sb.append("update ").append(table);
        sb.append(" set");
        sb.append("<trim prefix=\"\" suffix=\"\" suffixOverrides=\",\">");
        fieldNames.forEach(
                fieldName -> sb
                        .append("<if test=\"rows[0].").append(fieldName).append(" != null\">")
                        .append(StringUtil.humpToLine(fieldName))
                        .append(" = ")
                        .append("<foreach collection=\"list\" item=\"entry\" separator=\" \" open=\"case id \" close=\" end \">")
                        .append("when #{entry.id} then #{entry.").append(fieldName).append("}")
                        .append("</foreach>")
                        .append(StringUtil.COMMA).append(" ")
                        .append("</if>")
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
