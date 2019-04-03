package com.jbenitoc.domain.store;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemPriceTest {

    @Test
    void giveNullPrice_whenCreate_thenItThrowsAnException() {
        Exception e = assertThrows(ItemPriceIsNotValid.class, () -> ItemPrice.create(null));
        assertThat(e.getMessage()).isEqualTo("Item price is not valid, it cannot be null or zero");
    }

    @Test
    void givenNegativePrice_whenCreate_thenItThrowsAnException() {
        Exception e = assertThrows(ItemPriceIsNotValid.class, () -> ItemPrice.create(BigDecimal.valueOf(-1.0d)));
        assertThat(e.getMessage()).isEqualTo("Item price [-1.000000] is not valid, it cannot be negative");
    }

    @Test
    void givenValidPrice_whenCreate_thenItWorks() {
        BigDecimal price = BigDecimal.valueOf(10.5);

        ItemPrice itemPrice = ItemPrice.create(price);

        assertThat(itemPrice.getValue()).isEqualTo(price);
    }
}