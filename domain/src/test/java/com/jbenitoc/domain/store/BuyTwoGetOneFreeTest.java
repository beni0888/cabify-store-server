package com.jbenitoc.domain.store;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static com.jbenitoc.domain.store.ItemQuantity.ONE;
import static org.assertj.core.api.Assertions.assertThat;

class BuyTwoGetOneFreeTest {

    private static final ItemCode ITEM_CODE = ItemCode.create("ITEM");
    private static final ItemPrice PRICE = ItemPrice.create(BigDecimal.ONE);

    private BuyTwoGetOneFree discount = new BuyTwoGetOneFree(ITEM_CODE);

    @Test
    void givenOneItem_whenIsApplicable_thenReturnFalse() {
        CartEntry entry = CartEntry.create(ITEM_CODE, ONE);

        assertThat(discount.isApplicable(entry)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = {2,3,99999})
    void givenMoreThanOneItem_whenIsApplicable_thenReturnTrue(int quantity) {
        CartEntry entry = CartEntry.create(ITEM_CODE, ItemQuantity.create(quantity));

        assertThat(discount.isApplicable(entry)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("getAmountProvider")
    void givenACartEntryAndItemPrice_whenGetAmount_thenItReturnsTheTotalAmountForTheEntry(CartEntry entry, ItemPrice price, ItemPrice expectedAmount) {
        ItemPrice amount = discount.getAmount(entry, price);

        assertThat(amount).isEqualTo(expectedAmount);
    }

    private static Stream getAmountProvider() {
        return Stream.of(
                Arguments.of(CartEntry.create(ITEM_CODE, ONE), PRICE, PRICE),
                Arguments.of(CartEntry.create(ITEM_CODE, ItemQuantity.create(2)), PRICE, PRICE),
                Arguments.of(CartEntry.create(ITEM_CODE, ItemQuantity.create(3)), PRICE, ItemPrice.create(BigDecimal.valueOf(2))),
                Arguments.of(CartEntry.create(ITEM_CODE, ItemQuantity.create(3)), ItemPrice.create(BigDecimal.valueOf(1.5)), ItemPrice.create(BigDecimal.valueOf(3)))
        );
    }
}