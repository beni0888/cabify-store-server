package com.jbenitoc.domain.store;

import com.jbenitoc.domain.ValueObject;

import java.math.BigDecimal;

public final class CartTotalAmount extends ValueObject<BigDecimal> {

    private CartTotalAmount(BigDecimal value) {
        super(value);
    }

    public static CartTotalAmount create(BigDecimal value) {
        assertValid(value);
        return new CartTotalAmount(value);
    }

    private static void assertValid(BigDecimal value) {
        if (value == null) {
            throw CartTotalAmountIsNotValid.fromNull();
        }
        if (isNegative(value)) {
            throw CartTotalAmountIsNotValid.fromValue(value);
        }
    }

    private static boolean isNegative(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartTotalAmount cartTotalAmount = (CartTotalAmount) o;
        return value.compareTo(cartTotalAmount.value) == 0;
    }
}
