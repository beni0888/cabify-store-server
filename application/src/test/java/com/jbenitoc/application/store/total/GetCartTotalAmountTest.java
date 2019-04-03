package com.jbenitoc.application.store.total;

import com.jbenitoc.domain.store.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetCartTotalAmountTest {

    private PriceCalculator priceCalculator;
    private CartRepository cartRepository;
    private GetCartTotalAmount getCartTotalAmount;

    @BeforeEach
    void setUp() {
        priceCalculator = mock(PriceCalculator.class);
        cartRepository = mock(CartRepository.class);
        getCartTotalAmount = new GetCartTotalAmount(cartRepository, priceCalculator);
    }

    @Test
    void givenAQuery_whenExecute_thenItReturnsTheCartTotalAmount() {
        Cart cart = Cart.create(CartId.create());
        Price expectedTotal = Price.create(BigDecimal.TEN);

        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
        when(priceCalculator.calculateTotalAmount(cart)).thenReturn(expectedTotal);

        GetCartTotalAmountQuery query = new GetCartTotalAmountQuery(cart.getId().toString());
        Price totalAmount = getCartTotalAmount.execute(query);

        assertThat(totalAmount).isEqualTo(expectedTotal);
    }

    @Test
    void givenNonExistentCartId_whenExecute_thenThrowsItThrowsAnException() {
        CartId nonExistentCartId = CartId.create();

        Exception e = assertThrows(CartDoesNotExist.class, () -> {
            when(cartRepository.findById(nonExistentCartId)).thenReturn(Optional.empty());

            GetCartTotalAmountQuery query = new GetCartTotalAmountQuery(nonExistentCartId.toString());
            getCartTotalAmount.execute(query);
        });

        assertThat(e.getMessage()).isEqualTo(String.format("There is no cart with given ID [%s]", nonExistentCartId));
    }

    @Test
    void givenInvalidCartId_whenExecute_thenThrowsItThrowsAnException() {
        String invalidCartId = "not-valid-uuid";

        Exception e = assertThrows(CartIdIsNotValid.class, () -> {
            GetCartTotalAmountQuery query = new GetCartTotalAmountQuery(invalidCartId);
            getCartTotalAmount.execute(query);
        });

        assertThat(e.getMessage()).isEqualTo("Cart ID [not-valid-uuid] is not valid, it should be a valid UUID");
    }
}