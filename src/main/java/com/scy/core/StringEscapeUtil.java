package com.scy.core;

import org.apache.commons.text.StringEscapeUtils;

/**
 * @author : shichunyang
 * Date    : 2022/6/30
 * Time    : 12:59 下午
 * ---------------------------------------
 * Desc    : StringEscapeUtil
 */
public class StringEscapeUtil {

    public static String escapeJava(String str) {
        return StringEscapeUtils.escapeJava(str);
    }

    public static String escapeJson(String str) {
        return StringEscapeUtils.escapeJson(str);
    }

    public static String unescapeJava(String str) {
        return StringEscapeUtils.unescapeJava(str);
    }

    public static String unescapeJson(String str) {
        return StringEscapeUtils.unescapeJson(str);
    }
}
