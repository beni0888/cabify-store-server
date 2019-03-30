package com.jbenitoc.domain.store;

import com.jbenitoc.domain.ValueObject;

public final class ItemQuantity extends ValueObject<Integer> {

    private ItemQuantity(Integer quantity) {
        super(quantity);
    }

    public static ItemQuantity create(Integer quantity) {
        assertValid(quantity);
        return new ItemQuantity(quantity);
    }

    private static void assertValid(Integer quantity) {
        if (quantity == null || quantity < 1) {
            throw new ItemQuantityIsNotValid();
        }
    }
}
