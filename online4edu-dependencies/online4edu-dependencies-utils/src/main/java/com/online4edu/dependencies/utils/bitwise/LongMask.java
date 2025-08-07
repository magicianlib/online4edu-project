package com.online4edu.dependencies.utils.bitwise;

/**
 * long 二进制掩码操作
 * <pre>
 * {@code
 * public enum ExampleMask implements IntMask {
 *     JD,
 *     DOU_YIN,
 *     RED_BOOK,
 *     ALIBABA,
 *     TENCENT;
 *
 *     @Override
 *     public long index() {
 *         return this.ordinal();
 *     }
 * }
 * }
 * </pre>
 *
 * @author magicianlib@gmail.com
 * @see IntMask
 */
public interface LongMask {
    long index();

    default long clean(long bitmask) {
        return set(bitmask, false);
    }

    default long set(long bitmask, boolean value) {
        long mask = 1L << index();
        if (value) {
            return bitmask | mask;
        }
        return bitmask & ~mask;
    }

    default boolean test(long bitmask) {
        return get(bitmask);
    }

    default boolean get(long bitmask) {
        return (bitmask & (1L << index())) != 0L;
    }

    static String binary(long bitmask) {
        return Long.toBinaryString(bitmask);
    }
}
