package io.ituknown.utils.bitwise;

import java.util.Collection;

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
 *         return this.ordinal() + 1;
 *     }
 * }
 * }
 * </pre>
 *
 * @author magicianlib@gmail.com
 * @see LongMask
 */
public interface IntMask {
    /**
     * 索引小于或等于0，无效（不设置 mask）
     */
    int index();

    /**
     * 清除 mask
     */
    default int clean(int bitmask) {
        return set(bitmask, false);
    }

    default int set(int bitmask) {
        return set(bitmask, true);
    }

    /**
     * 设置 mask
     */
    default int set(int bitmask, boolean value) {
        if (index() <= 0) {
            return bitmask;
        }
        if (bitmask < 0) {
            bitmask = 0;
        }

        int mask = 1 << index();
        if (value) {
            return bitmask | mask;
        }
        return bitmask & ~mask;
    }

    /**
     * 测试是否已设置 mask
     */
    default boolean test(int bitmask) {
        return (bitmask & (1 << index())) != 0;
    }

    static int clean(int bitmask, Collection<IntMask> masks) {
        for (IntMask mask : masks) {
            bitmask = mask.clean(bitmask);
        }
        return bitmask;
    }

    static int set(Collection<IntMask> masks) {
        return set(0, masks);
    }

    static int set(int bitmask, Collection<IntMask> masks) {
        for (IntMask mask : masks) {
            bitmask = mask.set(bitmask);
        }
        return bitmask;
    }
}