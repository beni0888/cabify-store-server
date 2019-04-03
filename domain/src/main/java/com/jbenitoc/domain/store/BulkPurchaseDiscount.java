package com.jbenitoc.domain.store;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class BulkPurchaseDiscount implements Discount {
    private ItemCode itemCode;
    private ItemQuantity minimumQuantity;
    private Price priceWithDiscount;

    @Override
    public boolean isApplicable(CartEntry entry) {
        return entry.getItemCode().equals(itemCode) && entry.getQuantity().higherOrEqualThan(minimumQuantity);
    }

    @Override
    public Price getAmount(CartEntry entry, Price price) {
        if (isApplicable(entry)) {
            return priceWithDiscount.multiply(entry.getQuantity().toInteger());
        }
        return price.multiply(entry.getQuantity().toInteger());
    }
}
