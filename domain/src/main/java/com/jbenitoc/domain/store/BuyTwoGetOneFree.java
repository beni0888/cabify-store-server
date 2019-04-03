package com.jbenitoc.domain.store;

import lombok.AllArgsConstructor;

import static com.jbenitoc.domain.store.ItemQuantity.ONE;

@AllArgsConstructor
public class BuyTwoGetOneFree implements Discount {

    private ItemCode itemCode;

    @Override
    public boolean isApplicable(CartEntry entry) {
        return entry.getItemCode().equals(itemCode) && entry.getQuantity().higherThan(ONE);
    }

    @Override
    public Price getAmount(CartEntry entry, Price price) {
        int chunks = entry.getQuantity().getValue() / 2;
        int rest = entry.getQuantity().getValue() % 2;
        return price.multiply(chunks).add(price.multiply(rest));
    }
}
