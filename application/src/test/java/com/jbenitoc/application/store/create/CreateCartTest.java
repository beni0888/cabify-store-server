package com.jbenitoc.application.store.create;

import com.jbenitoc.domain.store.Cart;
import com.jbenitoc.domain.store.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreateCartTest {

    CreateCart createCart;
    CartRepository cartRepository;

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        createCart = new CreateCart(cartRepository);
    }

    @Test
    void givenCreateCart_whenExecute_thenANewCartIsCreated() {
        Cart cart = createCart.execute();

        verify(cartRepository, times(1)).save(cart);

        assertThat(cart).isNotNull();
        assertThat(cart.getId().getValue()).isNotNull();
    }
}