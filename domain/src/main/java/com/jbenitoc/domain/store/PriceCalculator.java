package com.jbenitoc.domain.store;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class PriceCalculator {

    private ItemRepository itemRepository;

    public CartTotalAmount calculateTotalAmount(Cart cart) {
        BigDecimal total = BigDecimal.ZERO;

        for (CartEntry entry: cart.getEntries().values()) {
            total = total.add(calculateEntryAmount(entry));
        }
        return CartTotalAmount.create(total);
    }

    private BigDecimal calculateEntryAmount(CartEntry entry) {
        BigDecimal itemPrice = getItemPrice(entry);
        return itemPrice.multiply(getItemQuantity(entry));
    }

    private BigDecimal getItemQuantity(CartEntry entry) {
        return BigDecimal.valueOf(entry.getQuantity().getValue());
    }

    private BigDecimal getItemPrice(CartEntry entry) {
        return itemRepository.findByCode(entry.getItemCode())
                .map(Item::getPrice)
                .orElseThrow(RuntimeException::new)
                .getValue();
    }
}
