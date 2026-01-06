package io.ituknown.utils.money;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WeightedValue<T> {
    private T id;
    private BigDecimal weight;
    private BigDecimal result = BigDecimal.ZERO;

    public WeightedValue() {
    }

    public WeightedValue(T id, BigDecimal weight) {
        this.id = id;
        this.weight = weight;
    }

    public void add(BigDecimal value) {
        result = result.add(value);
    }
}