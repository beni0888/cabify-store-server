package com.jbenitoc.domain.store;

public interface Discount {

    boolean isApplicable(CartEntry entry);

    Price getAmount(CartEntry entry, Price price);

    String getDiscountCode();
}
