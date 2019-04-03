package com.jbenitoc.domain.store;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

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

    @Test
    void givenMultipleItems_whenAddItemIsCalledMultipleTimes_thenAllItemsAreAddedToCart() {
        Item item1 = anItem("VOUCHER", "Cabify Voucher", BigDecimal.valueOf(5.0));
        Item item2 = anItem("TSHIRT", "Cabify T-shirt", BigDecimal.valueOf(1.0));

        cart.addEntry(item1, ItemQuantity.create(1));
        cart.addEntry(item2, ItemQuantity.create(1));

        Stream.of(item1, item2).forEach(item -> assertThat(cart.getEntry(item.getCode())).isNotEmpty());
    }

    private Item anItem(String code, String name, BigDecimal price) {
        return Item.create(ItemCode.create(code), ItemName.create(name), Price.create(price));
    }
}