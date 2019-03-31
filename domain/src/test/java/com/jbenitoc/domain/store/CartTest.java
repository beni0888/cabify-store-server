package com.jbenitoc.domain.store;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CartTest {

    private Cart cart = Cart.create(CartId.create());

    @Test
    void givenAnItemAndAQuantity_whenAddItem_thenGivenQuantityOfItemsAreAddedToCart() {
        Item item = anItem("VOUCHER", "Cabify Voucher", BigDecimal.valueOf(5.0));
        ItemQuantity quantity = ItemQuantity.create(1);

        cart.addEntry(item, quantity);
        Optional<CartEntry> cartEntry = cart.getEntry(item.getCode());

        assertThat(cartEntry).isNotEmpty();
        assertThat(cartEntry.get().getItemCode()).isEqualTo(item.getCode());
        assertThat(cartEntry.get().getQuantity()).isEqualTo(quantity);
    }

    @Test
    void givenAnItemWhichIsAlreadyInTheCartAndAQuantity_whenAddItem_thenTheQuantityOfTheGivenItemInTheCartIsIncreasedInTheGivenQuantity() {
        Item item = anItem("VOUCHER", "Cabify Voucher", BigDecimal.valueOf(5.0));
        cart.addEntry(item, ItemQuantity.create(1));

        cart.addEntry(item, ItemQuantity.create(2));
        Optional<CartEntry> cartEntry = cart.getEntry(item.getCode());

        assertThat(cartEntry.get().getQuantity()).isEqualTo(ItemQuantity.create(3));
    }

    private Item anItem(String code, String name, BigDecimal price) {
        return Item.create(ItemCode.create(code), ItemName.create(name), ItemPrice.create(price));
    }
}