package com.jbenitoc.domain.store;

import java.math.BigDecimal;

public final class ItemPriceIsNotValid extends IllegalArgumentException {

    private ItemPriceIsNotValid(String s) {
        super(s);
    }

    public static ItemPriceIsNotValid createForNull() {
        return new ItemPriceIsNotValid("Item price is not valid, it cannot be null");
    }

    public static ItemPriceIsNotValid createForNegative(BigDecimal price) {
        return new ItemPriceIsNotValid(String.format("Item price [%f] is not valid, it cannot be negative", price.floatValue()));
    }
}
