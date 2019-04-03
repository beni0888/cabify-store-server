package com.jbenitoc.domain.store;

import com.jbenitoc.domain.ValueObject;

public final class ItemQuantity extends ValueObject<Integer> {

    public static final ItemQuantity ONE = ItemQuantity.create(1);

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

    public ItemQuantity sum(ItemQuantity quantity) {
        return ItemQuantity.create(this.value + quantity.value);
    }

    public boolean higherThan(ItemQuantity quantity) {
        return this.value > quantity.value;
    }

    public boolean higherOrEqualThan(ItemQuantity quantity) {
        return this.value >= quantity.value;
    }
}
