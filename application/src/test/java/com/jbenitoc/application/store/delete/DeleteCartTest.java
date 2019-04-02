package com.jbenitoc.application.store.delete;

import com.jbenitoc.domain.store.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeleteCartTest {

    private DeleteCart deleteCart;
    private CartRepository cartRepository;

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        deleteCart = new DeleteCart(cartRepository);
    }

    @Test
    void givenCartId_whenExecute_thenTheCartIsDeleted() {
        CartId cartId = CartId.create();
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(aCart(cartId)));

        deleteCart.execute(new DeleteCartCommand(cartId.toString()));

        verify(cartRepository, times(1)).remove(any(Cart.class));
    }

    @Test
    void givenNonExistentCartId_whenExecuted_thenAnExceptionIsThrown() {
        CartId cartId = CartId.create();

        Exception e = assertThrows(CartDoesNotExist.class, () -> {
            when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

            deleteCart.execute(new DeleteCartCommand(cartId.toString()));

            verify(cartRepository, times(0)).remove(any(Cart.class));
        });

        assertThat(e.getMessage()).contains("There is no cart with given ID").contains(cartId.toString());
    }

    @Test
    void givenInvalidCartId_whenExecute_thenAnExceptionIsThrown() {
        Exception e = assertThrows(CartIdIsNotValid.class, () -> {
            String invalidId = "wrong";
            deleteCart.execute(new DeleteCartCommand(invalidId));
        });

        assertThat(e.getMessage()).contains("Cart ID [wrong] is not valid, it should be a valid UUID");
    }

    private Cart aCart(CartId cartId) {
        return Cart.create(cartId);
    }
}