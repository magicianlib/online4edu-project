package com.online4edu.dependencies.utils.datetime;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 日期格式化
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @since 2023/09/26 15:27
 */
public final class DateFormatUtil {

    /**
     * 以T分隔日期和时间, 并带时区信息, 符合ISO8601规范.
     */
    public static final String PATTERN_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";
    public static final String PATTERN_ISO_ON_SECOND = "yyyy-MM-dd'T'HH:mm:ssZZ";
    public static final String PATTERN_ISO_DATETIME = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * 以空格分隔日期和时间, 不带时区信息.
     */
    public static final String PATTERN_TIME = "HH:mm:ss";
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 符合ISO8601规范
     */
    public static final DateTimeFormatter FORMAT_ISO = DateTimeFormatter.ofPattern(PATTERN_ISO, Locale.getDefault());
    public static final DateTimeFormatter FORMAT_ISO_ON_SECOND = DateTimeFormatter.ofPattern(PATTERN_ISO_ON_SECOND, Locale.getDefault());
    public static final DateTimeFormatter FORMAT_ISO_DATETIME = DateTimeFormatter.ofPattern(PATTERN_ISO_DATETIME, Locale.getDefault());

    /**
     * 以空格分隔日期和时间, 不带时区信息
     */
    public static final DateTimeFormatter FORMAT_TIME = DateTimeFormatter.ofPattern(PATTERN_TIME, Locale.getDefault());
    public static final DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ofPattern(PATTERN_DATE, Locale.getDefault());
    public static final DateTimeFormatter FORMAT_DATETIME = DateTimeFormatter.ofPattern(PATTERN_DATETIME, Locale.getDefault());
}