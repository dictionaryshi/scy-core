package com.scy.core.format;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/19.
 */
@Slf4j
public class DateUtil {

    private DateUtil() {
    }

    public static final String PATTERN_MILLISECOND = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String PATTERN_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_HOUR = "yyyy-MM-dd HH";
    public static final String PATTERN_DAY = "yyyy-MM-dd";

    public static final String HUMP_PATTERN_MILLISECOND = "yyyyMMddHHmmssSSS";
    public static final String HUMP_PATTERN_SECOND = "yyyyMMddHHmmss";
    public static final String HUMP_PATTERN_MINUTE = "yyyyMMddHHmm";
    public static final String HUMP_PATTERN_HOUR = "yyyyMMddHH";
    public static final String HUMP_PATTERN_DAY = "yyyyMMdd";

    public static final String DEFAULT_TIME = "1970-01-01 08:00:01";

    public static final String OO = "00";

    /**
     * 格林威治时间
     */
    public static final String PATTERN_GMT = "EEE, dd MMM yyyy HH:mm:ss 'GMT'";
    public static final String GMT = "GMT";

    public static String date2Str(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }
}
