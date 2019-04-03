package com.jbenitoc.domain.store;

import com.jbenitoc.domain.ValueObject;

import java.math.BigDecimal;

public final class ItemPrice extends ValueObject<BigDecimal> {

    private ItemPrice(BigDecimal price) {
        super(price);
    }

    public static ItemPrice create(BigDecimal price) {
        assertValid(price);
        return new ItemPrice(price);
    }

    private static void assertValid(BigDecimal price) {
        if (price == null) {
            throw ItemPriceIsNotValid.createForNull();
        }
        if (isNegative(price)) {
            throw ItemPriceIsNotValid.createForNegative(price);
        }
    }

    private static boolean isNegative(BigDecimal price) {
        return (price.compareTo(BigDecimal.ZERO) < 0);
    }

    public ItemPrice multiply(int factor) {
        return ItemPrice.create(value.multiply(BigDecimal.valueOf(factor)));
    }

    public ItemPrice add(ItemPrice amount) {
        return ItemPrice.create(value.add(amount.value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPrice itemPrice = (ItemPrice) o;
        return value.compareTo(itemPrice.value) == 0;
    }
}
