package io.ituknown.utils.money;

import java.math.BigDecimal;

public enum BigDecimalUtils {
    ;

    public static boolean isBiggerThan(BigDecimal source, BigDecimal compareTo) {
        if (source == null) {
            source = BigDecimal.ZERO;
        }
        if (compareTo == null) {
            compareTo = BigDecimal.ZERO;
        }
        return source.compareTo(compareTo) > 0;
    }


    public static boolean isBiggerThanOrEq(BigDecimal source, BigDecimal compareTo) {
        if (source == null) {
            source = BigDecimal.ZERO;
        }
        if (compareTo == null) {
            compareTo = BigDecimal.ZERO;
        }
        return source.compareTo(compareTo) >= 0;
    }

    public static boolean isLessThan(BigDecimal source, BigDecimal compareTo) {
        return !isBiggerThanOrEq(source, compareTo);
    }

    public static boolean isLessThanOrEq(BigDecimal source, BigDecimal compareTo) {
        return !isBiggerThan(source, compareTo);
    }

    public static boolean isLessThanZero(BigDecimal source) {
        return isLessThan(source, BigDecimal.ZERO);
    }

    public static boolean isLessThanZeroOrEqZero(BigDecimal source) {
        return isLessThanOrEq(source, BigDecimal.ZERO);
    }

    public static boolean isBiggerThanZero(BigDecimal source) {
        return isBiggerThan(source, BigDecimal.ZERO);
    }

    public static boolean isBiggerThanOrEqZero(BigDecimal source) {
        return isBiggerThanOrEq(source, BigDecimal.ZERO);
    }

    public static boolean isEqZero(BigDecimal source) {
        return isEq(source, BigDecimal.ZERO);
    }

    public static boolean isNotEqZero(BigDecimal source) {
        return !isEq(source, BigDecimal.ZERO);
    }

    public static boolean isEq(BigDecimal source, BigDecimal compareTo) {
        if (source == null) {
            source = BigDecimal.ZERO;
        }
        if (compareTo == null) {
            compareTo = BigDecimal.ZERO;
        }
        return source.compareTo(compareTo) == 0;
    }
}