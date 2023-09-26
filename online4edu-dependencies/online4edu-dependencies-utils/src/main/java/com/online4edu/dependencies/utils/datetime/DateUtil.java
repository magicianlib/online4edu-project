package com.online4edu.dependencies.utils.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.function.Function;

/**
 * 日期格式化
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @since 2023/09/26 15:27
 */
public final class DateUtil {

    public static <R extends Temporal> R plus(Temporal date, long duration, ChronoUnit unit, Function<Temporal, R> function) {
        return function.apply(date.plus(duration, unit));
    }

    //////////// LocalDateTime //////////////

    public static LocalDateTime plus(LocalDateTime date, long duration, ChronoUnit unit) {
        return date.plus(duration, unit);
    }

    public static LocalDateTime plusSeconds(LocalDateTime date, long duration) {
        return date.plusSeconds(duration);
    }

    public static LocalDateTime plusMinutes(LocalDateTime date, long duration) {
        return date.plusMinutes(duration);
    }

    public static LocalDateTime plusHours(LocalDateTime date, long duration) {
        return date.plusHours(duration);
    }

    public static LocalDateTime plusDays(LocalDateTime date, long duration) {
        return date.plusDays(duration);
    }

    public static LocalDateTime plusWeeks(LocalDateTime date, long duration) {
        return date.plusWeeks(duration);
    }

    public static LocalDateTime plusMonths(LocalDateTime date, long duration) {
        return date.plusMonths(duration);
    }

    public static LocalDateTime plusYears(LocalDateTime date, long duration) {
        return date.plusYears(duration);
    }

    //////////// LocalDate //////////////

    public static LocalDate plus(LocalDate date, long duration, ChronoUnit unit) {
        return date.plus(duration, unit);
    }

    public static LocalDate plusDays(LocalDate date, long duration) {
        return date.plusDays(duration);
    }

    public static LocalDate plusWeeks(LocalDate date, long duration) {
        return date.plusWeeks(duration);
    }

    public static LocalDate plusMonths(LocalDate date, long duration) {
        return date.plusMonths(duration);
    }

    public static LocalDate plusYears(LocalDate date, long duration) {
        return date.plusYears(duration);
    }

    //////////// LocalTime //////////////

    public static LocalTime plus(LocalTime date, long duration, ChronoUnit unit) {
        return date.plus(duration, unit);
    }

    public static LocalTime plusSeconds(LocalTime date, long duration) {
        return date.plusSeconds(duration);
    }

    public static LocalTime plusMinutes(LocalTime date, long duration) {
        return date.plusMinutes(duration);
    }

    public static LocalTime plusHours(LocalTime date, long duration) {
        return date.plusHours(duration);
    }
}