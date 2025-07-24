package com.online4edu.dependencies.utils.money;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public enum MoneyUtils {
    ;

    public static <T> void apportionByWeights(BigDecimal pie, List<Weight<T>> weights) {
        apportionByWeights(pie, weights, 2);
    }

    /**
     * 根据权重平摊份额
     *
     * @param pie     总份额
     * @param weights 权重
     * @param scale   保留小数位数
     */
    public static <T> void apportionByWeights(BigDecimal pie, List<Weight<T>> weights, int scale) {
        if (Objects.isNull(pie) || CollectionUtils.isEmpty(weights)) {
            throw new RuntimeException("分摊权重未设置");
        }

        long distinct = weights.stream().map(Weight::getId).distinct().count();
        if (weights.size() != distinct) {
            throw new RuntimeException("存在重复权重值");
        }

        BigDecimal totalWeight = weights.stream().map(Weight::getWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (BigDecimalUtils.isEqZero(totalWeight)) {
            throw new RuntimeException("分摊权重未设置");
        }

        Weight<T> lastWeight = weights.get(0);
        Weight<T> lastNotZeroWeight = null;
        for (Weight<T> weight : weights) {
            lastWeight = weight;

            // DECIMAL32防止无限循环小数导致异常，小数点后2位的直接舍去
            BigDecimal result = weight.getWeight().divide(totalWeight, MathContext.DECIMAL32).multiply(pie).setScale(scale, RoundingMode.DOWN);
            weight.setResult(result);

            if (BigDecimalUtils.isNotEqZero(weight.getWeight())) {
                lastNotZeroWeight = weight;
            }
        }

        // 已分摊
        BigDecimal allocated = weights.stream().map(Weight::getResult).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal residue = pie.subtract(allocated); // 剩余

        // 如果最后一个权重值为0，表示该权重不参与分摊计算，将最后剩余的
        // 部分累加全部给最后一个权重不为0的数据
        if (BigDecimalUtils.isEqZero(lastWeight.getWeight())) {
            if (Objects.nonNull(lastNotZeroWeight)) {
                lastNotZeroWeight.add(residue);
            }
        } else {
            lastWeight.add(residue);
        }
    }

    public static <T> void main(String[] args) {

        BigDecimal total = new BigDecimal("100");
        ArrayList<Weight<String>> weights = Lists.newArrayList(
                new Weight<>("1", new BigDecimal("10")),
                new Weight<>("2", new BigDecimal("10")),
                new Weight<>("2", new BigDecimal("10")),
                new Weight<>("3", new BigDecimal("10")),
                new Weight<>("3", new BigDecimal("0"))
        );

        apportionByWeights(total, weights);
        for (Weight<String> weight : weights) {
            System.out.println(weight.getId() + " --------- " + weight.getWeight() + " --------- " + weight.getResult());
        }

    }
}
