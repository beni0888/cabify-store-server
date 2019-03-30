package com.jbenitoc.domain.store;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ItemQuantityTest {

    @Test
    void givenNullQuantity_whenCreate_thenItThrowsAnException() {
        Exception e = assertThrows(ItemQuantityIsNotValid.class, () -> ItemQuantity.create(null));
        assertThat(e.getMessage()).isEqualTo("Item quantity is not valid, it should be a positive integer");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void givenNonPositiveQuantity_whenCreate_thenItThrowsAnException(int quantity) {
        Exception e = assertThrows(ItemQuantityIsNotValid.class, () -> ItemQuantity.create(quantity));
        assertThat(e.getMessage()).isEqualTo("Item quantity is not valid, it should be a positive integer");
    }

    @Test
    void givenValidQuantity_whenCreate_thenItWorks() {
        Integer quantity = 1;

        ItemQuantity itemQuantity = ItemQuantity.create(quantity);

        assertThat(itemQuantity.getValue()).isEqualTo(quantity);
    }
}