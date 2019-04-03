package com.jbenitoc.domain.store;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
public class PriceCalculator {

    private ItemRepository itemRepository;
    private DiscountRepository discountRepository;

    public Price calculateTotalAmount(Cart cart) {
        Price total = Price.create(BigDecimal.ZERO);

        for (CartEntry entry: cart.getEntries().values()) {
            total = total.add(calculateEntryAmount(entry));
        }
        return total;
    }

    private Price calculateEntryAmount(CartEntry entry) {
        Price itemPrice = getItemPrice(entry);
        Optional<Discount> discount = discountRepository.findAll().stream()
                .filter(disc -> disc.isApplicable(entry)).findFirst();

        return discount.map(discount1 -> discount1.getAmount(entry, itemPrice))
                .orElse(itemPrice.multiply(entry.getQuantity().toInteger()));
    }

    private Price getItemPrice(CartEntry entry) {
        return itemRepository.findByCode(entry.getItemCode())
                .map(Item::getPrice)
                .orElseThrow(RuntimeException::new);
    }
}
