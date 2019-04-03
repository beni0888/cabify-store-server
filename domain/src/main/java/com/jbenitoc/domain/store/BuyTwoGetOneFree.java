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
    public ItemPrice getAmount(CartEntry entry, ItemPrice itemPrice) {
        int chunks = entry.getQuantity().getValue() / 2;
        int rest = entry.getQuantity().getValue() % 2;
        return itemPrice.multiply(chunks).add(itemPrice.multiply(rest));
    }
}
