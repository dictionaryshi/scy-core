package com.scy.core;

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
}
