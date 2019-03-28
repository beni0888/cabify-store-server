package com.jbenitoc.model.store;

import java.math.BigDecimal;

public class ItemPriceIsNotValid extends IllegalArgumentException {

    private ItemPriceIsNotValid(String s) {
        super(s);
    }

    public static ItemPriceIsNotValid createForNullOrZero() {
        return new ItemPriceIsNotValid("Item price is not valid, it cannot be null or zero");
    }

    public static ItemPriceIsNotValid createForNegative(BigDecimal price) {
        return new ItemPriceIsNotValid(String.format("Item price [%f] is not valid, it cannot be negative", price.floatValue()));
    }
}
