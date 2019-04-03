package com.jbenitoc.domain.store;

import java.math.BigDecimal;

public final class ItemPriceIsNotValid extends IllegalArgumentException {

    private ItemPriceIsNotValid(String s) {
        super(s);
    }

    public static ItemPriceIsNotValid createForNull() {
        return new ItemPriceIsNotValid("Price is not valid, it cannot be null");
    }

    public static ItemPriceIsNotValid createForNegative(BigDecimal price) {
        return new ItemPriceIsNotValid(String.format("Price [%f] is not valid, it cannot be negative", price.floatValue()));
    }
}
