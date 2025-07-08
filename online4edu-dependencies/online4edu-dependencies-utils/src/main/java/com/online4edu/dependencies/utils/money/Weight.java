package com.online4edu.dependencies.utils.money;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Weight {
    private Object id;
    private BigDecimal weight;
    private BigDecimal result = BigDecimal.ZERO;

    public Weight(Object id, BigDecimal weight) {
        this.id = id;
        this.weight = weight;
    }

    public void add(BigDecimal value) {
        result = result.add(value);
    }
}