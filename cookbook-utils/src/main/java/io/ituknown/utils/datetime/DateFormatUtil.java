package io.ituknown.utils.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

/**
 * 日期格式化
 *
 * @author magicianlib@gmail.com
 * @since 2023/09/26 15:27
 */
public final class DateFormatUtil {

    /**
     * ISO8601 规范
     */
    public static final String ISO_DATE_PATTERN = "yyyyMMdd"; // 20111203
    public static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ofPattern(ISO_DATE_PATTERN, Locale.getDefault());

    public static final String ISO_LOCAL_DATE_PATTERN = "yyyy-MM-dd"; // 2011-12-03
    public static final DateTimeFormatter ISO_LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern(ISO_LOCAL_DATE_PATTERN, Locale.getDefault());

    public static final String ISO_LOCAL_TIME_PATTERN = "HH:mm:ss"; // 10:15:30
    public static final DateTimeFormatter ISO_LOCAL_TIME_FORMATTER = DateTimeFormatter.ofPattern(ISO_LOCAL_TIME_PATTERN, Locale.getDefault());

    public static final String ISO_LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"; // 2011-12-03T10:15:30
    public static final DateTimeFormatter ISO_LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(ISO_LOCAL_DATE_TIME_PATTERN, Locale.getDefault());

    /**
     * 自定义
     */
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"; // 2011-12-03 10:15:30
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN, Locale.getDefault());

    public static final String TIMESTAMP_PATTERN = "yyyyMMddHHmmss"; // 20111203101530
    public static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN, Locale.getDefault());

    public static final String SIMPLIFY_TIMESTAMP_PATTERN = "yyMMddHHmmss"; // 111203101530
    public static final DateTimeFormatter SIMPLIFY_TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern(SIMPLIFY_TIMESTAMP_PATTERN, Locale.getDefault());

    public static final String LOCAL_DATE_PATTERN = "yyyyMMdd"; // 20111203
    public static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern(LOCAL_DATE_PATTERN, Locale.getDefault());

    public static final String SIMPLIFY_LOCAL_DATE_PATTERN = "yyMMdd"; // 111203
    public static final DateTimeFormatter SIMPLIFY_LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern(SIMPLIFY_LOCAL_DATE_PATTERN, Locale.getDefault());

    /**
     * 带时区
     */
    public static final String TIME_ZONE_PATTERN = "HH:mm:ss OOOO"; // 10:15:30 GMT+08:00
    public static final DateTimeFormatter TIME_ZONE_FORMATTER = DateTimeFormatter.ofPattern(TIME_ZONE_PATTERN, Locale.getDefault());

    public static final String DATE_TIME_ZONE_PATTERN = "yyyy-MM-dd HH:mm:ss OOOO"; // 2011-12-03 10:15:30 GMT+08:00
    public static final DateTimeFormatter DATE_TIME_ZONE_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_ZONE_PATTERN, Locale.getDefault());

    /**
     * 获取时间戳
     *
     * @param simplify 简化版年份只保留后两位
     */
    public static String timestamp(TemporalAccessor temporal, boolean simplify) {
        return (simplify ? SIMPLIFY_TIMESTAMP_FORMATTER : TIMESTAMP_FORMATTER).format(temporal);
    }

    public static String timestamp() {
        return timestamp(LocalDateTime.now(), false);
    }

    /**
     * 获取日期
     *
     * @param simplify 简化版年份只保留后两位
     */
    public static String date(LocalDate date, boolean simplify) {
        return (simplify ? LOCAL_DATE_FORMATTER : SIMPLIFY_LOCAL_DATE_FORMATTER).format(date);
    }

    public static String date() {
        return date(LocalDate.now(), false);
    }
}