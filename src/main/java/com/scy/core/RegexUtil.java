package com.scy.core;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RegexUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/3.
 */
public class RegexUtil {

    private RegexUtil() {
    }

    public static final String EMAIL = "\\w+@\\w+(\\.[a-zA-Z]{2,3})+";

    public static final String CELL_PHONE = "(\\d{3})\\d{4}(\\d{4})";

    public static String hideCellPhone(String cellPhone) {
        return cellPhone.replaceAll(CELL_PHONE, "$1****$2");
    }

    public static List<String> search(String source, String regex) {
        List<String> results = new ArrayList<>();
        Matcher matcher = Pattern.compile(regex).matcher(source);
        while (matcher.find()) {
            results.add(matcher.group());
        }
        return results;
    }
}
