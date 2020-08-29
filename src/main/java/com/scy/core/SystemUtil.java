package com.scy.core;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.TimeZone;

/**
 * SystemUtil
 *
 * @author shichunyang
 * Created by shichunyang on 2020/8/18.
 */
public class SystemUtil {

    private SystemUtil() {
    }

    /**
     * 系统换行符
     */
    public static final String SYSTEM_LINE_BREAK = System.lineSeparator();

    /**
     * 上海时区
     */
    public static final TimeZone TIME_ZONE_SHANG_HAI = TimeZone.getTimeZone(ZoneId.SHORT_IDS.get("CTT"));

    /**
     * 基础包名
     */
    public static final String BASE_PACKAGE = "com.scy";

    /**
     * 工作目录
     */
    public static final String WORKING_DIRECTORY = "user.dir";

    public static final Charset CHARSET_UTF_8 = StandardCharsets.UTF_8;

    public static final String CHARSET_UTF_8_STR = StandardCharsets.UTF_8.name();

    public static String getWorkingDirectory() {
        return System.getProperty(WORKING_DIRECTORY);
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
