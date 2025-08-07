package com.online4edu.dependencies.utils.bitwise;

/**
 * int 二进制掩码操作
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
 *     public int index() {
 *         return this.ordinal();
 *     }
 * }
 * }
 * </pre>
 *
 * @author magicianlib@gmail.com
 * @see LongMask
 */
public interface IntMask {
    int index();

    default int clean(int bitmask) {
        return set(bitmask, false);
    }

    default int set(int bitmask, boolean value) {
        int mask = 1 << index();
        if (value) {
            return bitmask |= mask;
        }
        return bitmask &= ~mask;
    }

    default boolean test(int bitmask) {
        return get(bitmask);
    }

    default boolean get(int bitmask) {
        return (bitmask & (1 << index())) != 0;
    }

    static String binary(int bitmask) {
        return Integer.toBinaryString(bitmask);
    }
}
