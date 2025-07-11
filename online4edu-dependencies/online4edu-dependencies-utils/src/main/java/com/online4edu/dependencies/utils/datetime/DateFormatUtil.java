package com.online4edu.dependencies.utils.datetime;

import java.time.format.DateTimeFormatter;
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
    public static final String BASIC_ISO_DATE_PATTERN = "yyyyMMdd"; // 20111203
    public static final String ISO_LOCAL_DATE_PATTERN = "yyyy-MM-dd"; // 2011-12-03
    public static final String ISO_LOCAL_TIME_PATTERN = "HH:mm:ss"; // 10:15:30
    public static final String ISO_LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"; // 2011-12-03T10:15:30
    /**
     * 自定义
     */
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"; // 2011-12-03 10:15:30

    public static final DateTimeFormatter BASIC_ISO_DATE = DateTimeFormatter.ofPattern(BASIC_ISO_DATE_PATTERN, Locale.getDefault());
    public static final DateTimeFormatter ISO_LOCAL_DATE = DateTimeFormatter.ofPattern(ISO_LOCAL_DATE_PATTERN, Locale.getDefault());
    public static final DateTimeFormatter ISO_LOCAL_TIME = DateTimeFormatter.ofPattern(ISO_LOCAL_TIME_PATTERN, Locale.getDefault());
    public static final DateTimeFormatter ISO_LOCAL_DATE_TIME = DateTimeFormatter.ofPattern(ISO_LOCAL_DATE_TIME_PATTERN, Locale.getDefault());
    public static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN, Locale.getDefault());
}