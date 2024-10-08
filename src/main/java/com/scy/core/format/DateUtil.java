package com.scy.core.format;

import com.scy.core.StringUtil;
import com.scy.core.SystemUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

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

    public static final long SECOND = 1000L;

    public static final long MINUTE = 60 * SECOND;

    public static final long HOUR = 60 * MINUTE;

    public static final long DAY = 24 * HOUR;

    public static final DateTimeFormatter ISO_LOCAL_DATE = DateTimeFormatter.ISO_LOCAL_DATE;

    public static final DateTimeFormatter ISO_LOCAL_TIME = DateTimeFormatter.ISO_LOCAL_TIME;

    public static final DateTimeFormatter ISO_LOCAL_DATE_TIME = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static final DateTimeFormatter ISO_WEEK_DATE = DateTimeFormatter.ISO_WEEK_DATE;

    public static final DateTimeFormatter BASIC_ISO_DATE = DateTimeFormatter.BASIC_ISO_DATE;

    public static final DateTimeFormatter FORMATTER_PATTERN_MILLISECOND = DateTimeFormatter.ofPattern(PATTERN_MILLISECOND);

    public static final DateTimeFormatter FORMATTER_PATTERN_SECOND = DateTimeFormatter.ofPattern(PATTERN_SECOND);

    public static final DateTimeFormatter FORMATTER_PATTERN_MINUTE = DateTimeFormatter.ofPattern(PATTERN_MINUTE);

    public static final DateTimeFormatter FORMATTER_PATTERN_HOUR = DateTimeFormatter.ofPattern(PATTERN_HOUR);

    public static final DateTimeFormatter FORMATTER_HUMP_PATTERN_MILLISECOND = DateTimeFormatter.ofPattern(HUMP_PATTERN_MILLISECOND);

    public static final DateTimeFormatter FORMATTER_HUMP_PATTERN_SECOND = DateTimeFormatter.ofPattern(HUMP_PATTERN_SECOND);

    public static final DateTimeFormatter FORMATTER_HUMP_PATTERN_MINUTE = DateTimeFormatter.ofPattern(HUMP_PATTERN_MINUTE);

    public static final DateTimeFormatter FORMATTER_HUMP_PATTERN_HOUR = DateTimeFormatter.ofPattern(HUMP_PATTERN_HOUR);

    public static int millisecond2Second(long millisecond) {
        return (int) (millisecond / SECOND);
    }

    public static double millisecond2Minute(long millisecond) {
        return NumberUtil.divide(millisecond, 1, MINUTE).doubleValue();
    }

    public static Date getCurrentDate() {
        return new Date();
    }

    public static String getCurrentDateStr() {
        return FORMATTER_PATTERN_SECOND.format(nowLocalDateTime());
    }

    public static Date str2Date(String dateStr, String pattern) {
        if (StringUtil.isEmpty(dateStr)) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(dateStr);
        } catch (Exception e) {
            log.error(MessageUtil.format("str2Date error", e, "dateStr", dateStr, "pattern", pattern));
            return null;
        }
    }

    public static LocalDateTime str2LocalDateTime(String dateStr, String pattern) {
        if (StringUtil.isEmpty(dateStr)) {
            return null;
        }

        try {
            return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            log.error(MessageUtil.format("str2LocalDateTime error", e, "dateStr", dateStr, "pattern", pattern));
            return null;
        }
    }

    public static String date2Str(Date date, String pattern) {
        if (Objects.isNull(date)) {
            return StringUtil.EMPTY;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static String localDateTime2Str(LocalDateTime localDateTime, String pattern) {
        if (Objects.isNull(localDateTime)) {
            return StringUtil.EMPTY;
        }

        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static Date getDayOffset(Date date, int offset) {
        return getDateOffset(date, Calendar.DAY_OF_MONTH, offset);
    }

    public static Date getHourOffset(Date date, int offset) {
        return getDateOffset(date, Calendar.HOUR_OF_DAY, offset);
    }

    public static Date getMinuteOffset(Date date, int offset) {
        return getDateOffset(date, Calendar.MINUTE, offset);
    }

    public static Date getSecondOffset(Date date, int offset) {
        return getDateOffset(date, Calendar.SECOND, offset);
    }

    private static Date getDateOffset(Date date, int field, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, offset);
        return cal.getTime();
    }

    public static LocalDateTime getDayOffset(LocalDateTime localDateTime, int offset) {
        return localDateTime.plusDays(offset);
    }

    public static LocalDateTime getHourOffset(LocalDateTime localDateTime, int offset) {
        return localDateTime.plusHours(offset);
    }

    public static LocalDateTime getMinuteOffset(LocalDateTime localDateTime, int offset) {
        return localDateTime.plusMinutes(offset);
    }

    public static LocalDateTime getSecondOffset(LocalDateTime localDateTime, int offset) {
        return localDateTime.plusSeconds(offset);
    }

    public static SimpleDateFormat getGmtSimpleDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_GMT, Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(GMT));
        return simpleDateFormat;
    }

    public static Date getDayStartTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getDayEndTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    public static Date getMonthStartTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getMonthEndTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    public static LocalDateTime getDayStartTime(LocalDateTime localDateTime) {
        return localDateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public static LocalDateTime getDayEndTime(LocalDateTime localDateTime) {
        return localDateTime.withHour(23).withMinute(59).withSecond(59);
    }

    public static LocalDateTime getMonthStartTime(LocalDateTime localDateTime) {
        return localDateTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public static LocalDateTime getMonthEndTime(LocalDateTime localDateTime) {
        return localDateTime.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);
    }

    public static LocalDateTime nowLocalDateTime() {
        return LocalDateTime.now(SystemUtil.ZONE_ID_SHANG_HAI);
    }

    public static long toEpochMilli(LocalDateTime localDateTime) {
        return localDateTime.atZone(SystemUtil.ZONE_ID_SHANG_HAI).toInstant().toEpochMilli();
    }

    public static LocalDateTime date2LocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), SystemUtil.ZONE_ID_SHANG_HAI);
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(SystemUtil.ZONE_ID_SHANG_HAI).toInstant());
    }

    public static LocalDateTime millis2LocalDateTime(long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), SystemUtil.ZONE_ID_SHANG_HAI);
    }

    public static long between(Temporal start, Temporal end, ChronoUnit chronoUnit) {
        return chronoUnit.between(start, end);
    }

    public static LocalDateTime toLocalDateTime(int hour, int minute, int second, int nanoOfSecond) {
        return LocalDateTime.of(LocalDate.now(SystemUtil.ZONE_ID_SHANG_HAI), LocalTime.of(hour, minute, second, nanoOfSecond));
    }
}
