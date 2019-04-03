package com.jbenitoc.domain.store;

import java.math.BigDecimal;

public class CartTotalAmountIsNotValid extends IllegalArgumentException {

    private CartTotalAmountIsNotValid(String s) {
        super(s);
    }

    public static CartTotalAmountIsNotValid fromNull() {
        return new CartTotalAmountIsNotValid("Cart total amount is not valid, it should not be null");
    }

    public static CartTotalAmountIsNotValid fromValue(BigDecimal value) {
        return new CartTotalAmountIsNotValid(String.format("Cart total amount [%s] is not valid, it cannot be a negative number", value));
    }
}
