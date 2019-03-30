package com.jbenitoc.domain.store;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CartIdIsNotValidTest {

    @Test
    void givenNullId_whenCreate_thenItThrowsAnException() {
        Exception e = assertThrows(CartIdIsNotValid.class, () -> CartId.create(null));
        assertThat(e.getMessage()).isEqualTo("Cart ID is not valid, it cannot be null");
    }

    @Test
    void givenValidId_whenCreate_thenItWorks() {
        UUID id = randomUUID();

        CartId cartId = CartId.create(id);

        assertThat(cartId.getValue()).isEqualTo(id);
    }
}