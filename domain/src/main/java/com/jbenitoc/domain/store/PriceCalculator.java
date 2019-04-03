package com.jbenitoc.domain.store;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
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

        return discount.map(discount1 -> {
            Price price = discount1.getAmount(entry, itemPrice);
            log.info("Applying discount to cart entry {} - Discount: {} - Total: {}", entry, discount1.getDiscountCode(), price);
            return price;
        }).orElse(itemPrice.multiply(entry.getQuantity().toInteger()));
    }

    private Price getItemPrice(CartEntry entry) {
        return itemRepository.findByCode(entry.getItemCode())
                .map(Item::getPrice)
                .orElseThrow(RuntimeException::new);
    }
}
