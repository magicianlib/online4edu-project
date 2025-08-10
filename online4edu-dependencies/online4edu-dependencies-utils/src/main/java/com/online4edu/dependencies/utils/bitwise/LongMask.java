package com.online4edu.dependencies.utils.bitwise;

import java.util.List;

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
 *         return this.ordinal() + 1;
 *     }
 * }
 * }
 * </pre>
 *
 * @author magicianlib@gmail.com
 * @see IntMask
 */
public interface LongMask {
    /**
     * 索引小于或等于0，无效（不设置 mask）
     */
    long index();

    /**
     * 清除 mask
     */
    default long clean(long bitmask) {
        return set(bitmask, false);
    }

    default long set(long bitmask) {
        return set(bitmask, true);
    }

    /**
     * 设置 mask
     */
    default long set(long bitmask, boolean value) {
        if (index() <= 0L) {
            return bitmask;
        }
        if (bitmask < 0L) {
            bitmask = 0L;
        }

        long mask = 1L << index();
        if (value) {
            return bitmask | mask;
        }
        return bitmask & ~mask;
    }

    /**
     * 测试是否已设置 mask
     */
    default boolean test(long bitmask) {
        return (bitmask & (1L << index())) != 0L;
    }

    static long clean(long bitmask, List<LongMask> masks) {
        for (LongMask mask : masks) {
            bitmask = mask.clean(bitmask);
        }
        return bitmask;
    }

    static long set(List<LongMask> masks) {
        return set(0L, masks);
    }

    static long set(long bitmask, List<LongMask> masks) {
        for (LongMask mask : masks) {
            bitmask = mask.set(bitmask);
        }
        return bitmask;
    }
}
