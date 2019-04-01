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
        assertThat(e.getMessage()).isEqualTo("Cart ID [null] is not valid, it should be a valid UUID");
    }

    @Test
    void givenInvalidUUID_whenCreate_thenItThrowsAnException() {
        String invalidUUID = "foo";
        Exception e = assertThrows(CartIdIsNotValid.class, () -> CartId.create(invalidUUID));
        assertThat(e.getMessage()).isEqualTo("Cart ID [foo] is not valid, it should be a valid UUID");
    }

    @Test
    void givenValidId_whenCreate_thenItWorks() {
        UUID id = randomUUID();

        CartId cartId = CartId.create(id.toString());

        assertThat(cartId.getValue()).isEqualTo(id);
    }
}