package com.jbenitoc.domain.store;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static com.jbenitoc.domain.store.ItemQuantity.ONE;
import static org.assertj.core.api.Assertions.assertThat;

class BulkPurchaseDiscountTest {

    private static final ItemCode ITEM_CODE = ItemCode.create("ITEM");
    private static final Price REGULAR_PRICE = Price.create(BigDecimal.TEN);
    private static final Price PRICE_WITH_DISCOUNT = Price.create(BigDecimal.ONE);

    private BulkPurchaseDiscount discount = new BulkPurchaseDiscount(ITEM_CODE, ItemQuantity.create(3), PRICE_WITH_DISCOUNT);

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void givenItemQuantityIsLessThanMinimum_whenIsApplicable_thenReturnFalse(int quantity) {
        CartEntry entry = CartEntry.create(ITEM_CODE, ItemQuantity.create(quantity));

        assertThat(discount.isApplicable(entry)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = {3,4,99999})
    void givenItemQuantityIsHigherOrEqualThanMinimum_whenIsApplicable_thenReturnTrue(int quantity) {
        CartEntry entry = CartEntry.create(ITEM_CODE, ItemQuantity.create(quantity));

        assertThat(discount.isApplicable(entry)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("getAmountProvider")
    void givenACartEntryAndItemPrice_whenGetAmount_thenItReturnsTheTotalAmountForTheEntry(CartEntry entry, Price price, Price expectedAmount) {
        Price amount = discount.getAmount(entry, price);

        assertThat(amount).isEqualTo(expectedAmount);
    }

    private static Stream getAmountProvider() {
        return Stream.of(
                Arguments.of(CartEntry.create(ITEM_CODE, ONE), REGULAR_PRICE, REGULAR_PRICE),
                Arguments.of(CartEntry.create(ITEM_CODE, ItemQuantity.create(2)), REGULAR_PRICE, Price.create(BigDecimal.valueOf(20))),
                Arguments.of(CartEntry.create(ITEM_CODE, ItemQuantity.create(3)), REGULAR_PRICE, Price.create(BigDecimal.valueOf(3))),
                Arguments.of(CartEntry.create(ITEM_CODE, ItemQuantity.create(99)), REGULAR_PRICE, Price.create(BigDecimal.valueOf(99)))
        );
    }
}