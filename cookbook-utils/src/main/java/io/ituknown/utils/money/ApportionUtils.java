package io.ituknown.utils.money;

import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 权重平摊 - Utils
 *
 * @author magicianlib@gmail.com
 */
public enum ApportionUtils {
    ;

    public static <T> Map<T, WeightedValue<T>> apportionMap(BigDecimal pie, List<WeightedValue<T>> weights) {
        return apportionMap(pie, weights, 2);
    }

    public static <T> Map<T, WeightedValue<T>> apportionMap(BigDecimal pie, List<WeightedValue<T>> weights, int scale) {
        apportion(pie, weights, scale);
        return weights.stream().collect(Collectors.toMap(WeightedValue::getId, Function.identity(), (oV, nV) -> nV));
    }

    public static <T> void apportion(BigDecimal pie, List<WeightedValue<T>> weights) {
        apportion(pie, weights, 2);
    }

    /**
     * 根据权重平摊份额
     *
     * @param pie     总份额
     * @param weights 权重
     * @param scale   保留小数位数
     */
    public static <T> void apportion(BigDecimal pie, List<WeightedValue<T>> weights, int scale) {
        if (Objects.isNull(pie) || CollectionUtils.isEmpty(weights)) {
            throw new RuntimeException("分摊权重未设置");
        }

        long distinct = weights.stream().map(WeightedValue::getId).distinct().count();
        if (weights.size() != distinct) {
            throw new RuntimeException("存在重复权重值");
        }

        BigDecimal totalWeight = weights.stream().map(WeightedValue::getWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (BigDecimalUtils.isEqZero(totalWeight)) {
            throw new RuntimeException("分摊权重未设置");
        }

        // 已分摊
        BigDecimal allocated = BigDecimal.ZERO;
        WeightedValue<T> lastNotZeroWeight = null;
        for (WeightedValue<T> weight : weights) {

            // 计算份额
            BigDecimal result = calculatePortion(pie, weight.getWeight(), totalWeight, scale);
            allocated = allocated.add(result);
            weight.setResult(result);

            if (BigDecimalUtils.isNotEqZero(weight.getWeight())) {
                lastNotZeroWeight = weight;
            }
        }

        // 剩余
        BigDecimal residue = pie.subtract(allocated);

        // 如果最后一个权重值为0，表示该权重不参与分摊计算，将最后剩余的
        // 部分累加全部给最后一个权重不为0的数据
        WeightedValue<T> lastWeight = weights.get(weights.size() - 1);
        if (BigDecimalUtils.isEqZero(lastWeight.getWeight())) {
            if (Objects.nonNull(lastNotZeroWeight)) {
                lastNotZeroWeight.add(residue);
            }
        } else {
            lastWeight.add(residue);
        }
    }

    /**
     * 份额计算
     *
     * @param pie         总份额
     * @param weight      权重
     * @param totalWeight 总权重
     */
    public static BigDecimal calculatePortion(BigDecimal pie, BigDecimal weight, BigDecimal totalWeight, int scale) {
        // 临时提高精度，防止误差放大
        // 中间用 HALF_UP 提高精度以减少累计误差
        // 最后统一用 DOWN 保证业务规则（如金额不能超出总和）
        int extraPrecision = scale + 6;
        BigDecimal ratio = weight.divide(totalWeight, extraPrecision, RoundingMode.HALF_UP);
        return pie.multiply(ratio).setScale(scale, RoundingMode.DOWN);
    }
}