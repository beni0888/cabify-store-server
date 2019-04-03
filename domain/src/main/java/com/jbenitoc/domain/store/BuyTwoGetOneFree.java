package com.jbenitoc.domain.store;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import static com.jbenitoc.domain.store.ItemQuantity.ONE;

@AllArgsConstructor
@EqualsAndHashCode
public class BuyTwoGetOneFree implements Discount {

    private ItemCode itemCode;

    @Override
    public boolean isApplicable(CartEntry entry) {
        return entry.getItemCode().equals(itemCode) && entry.getQuantity().higherThan(ONE);
    }

    @Override
    public Price getAmount(CartEntry entry, Price price) {
        Integer numberOfPacks = entry.getQuantity().getValue() / 2;
        Integer rest = entry.getQuantity().getValue() % 2;
        return price.multiply(numberOfPacks).add(price.multiply(rest));
    }

    @Override
    public String getDiscountCode() {
        return String.format("BUY_2_GET_1_FREE - %s", itemCode);
    }
}
