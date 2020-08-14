package com.scy.core;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * StringUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/14.
 */
public class StringUtil {

    private StringUtil() {
    }

    public static final String NULL = "null";

    public static final String EMPTY = "";

    public static final String COMMA = ",";
    public static final Pattern COMMA_PATTERN = Pattern.compile("[,，]+");

    public static final String LINE = "|";
    public static final Pattern LINE_PATTERN = Pattern.compile("[|｜]+");

    public static final String NUMBER_REGEX = "^[+-]?\\d+(\\.\\d+)?$";
    public static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty() || str.trim().toLowerCase().equals(NULL);
    }

    public static String format(String template, Object... args) {
        return String.format(template, args);
    }

    public static String join(Iterable<?> iterable, String separator) {
        return Joiner.on(separator).skipNulls().join(iterable);
    }

    public static List<String> split(String str, Pattern pattern) {
        return Splitter.on(pattern).trimResults().omitEmptyStrings().splitToList(str);
    }

    public static String replace(String text, String searchString, String replacement) {
        return StringUtils.replace(text, searchString, replacement);
    }

    public static String replaceIgnoreCase(String text, String searchString, String replacement) {
        return StringUtils.replaceIgnoreCase(text, searchString, replacement);
    }

    public static String removeStart(String str, String remove) {
        return StringUtils.removeStart(str, remove);
    }

    public static String removeStartIgnoreCase(String str, String remove) {
        return StringUtils.removeStartIgnoreCase(str, remove);
    }

    public static String removeEnd(String str, String remove) {
        return StringUtils.removeEnd(str, remove);
    }

    public static String removeEndIgnoreCase(String str, String remove) {
        return StringUtils.removeEndIgnoreCase(str, remove);
    }

    public static boolean isNumber(String str) {
        if (StringUtil.isEmpty(str)) {
            return Boolean.FALSE;
        }
        return NUMBER_PATTERN.matcher(str).matches();
    }
}
