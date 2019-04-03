package com.jbenitoc.domain.store;

import com.jbenitoc.domain.ValueObject;

import java.math.BigDecimal;

public final class Price extends ValueObject<BigDecimal> {

    private Price(BigDecimal price) {
        super(price);
    }

    public static Price create(BigDecimal price) {
        assertValid(price);
        return new Price(price);
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

    public Price multiply(ItemQuantity factor) {
        return Price.create(value.multiply(BigDecimal.valueOf(factor.getValue())));
    }

    public Price add(Price amount) {
        return Price.create(value.add(amount.value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return value.compareTo(price.value) == 0;
    }
}
