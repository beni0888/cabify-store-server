package com.jbenitoc.infrastructure.storage;

import com.jbenitoc.domain.store.Cart;
import com.jbenitoc.domain.store.CartId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryCartRepositoryTest {

    InMemoryCartRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryCartRepository();
    }

    @Test
    void givenACart_whenSave_thenItIsSaved() {
        Cart cart = Cart.create(CartId.create());

        repository.save(cart);
        Optional<Cart> retrievedCart = repository.findById(cart.getId());

        assertThat(retrievedCart).isNotEmpty();
        assertThat(retrievedCart.get()).isEqualTo(cart);
    }

    @Test
    void givenACartIdThatIsNotInTheRepository_whenFindById_thenItReturnsAnEmptyOptional() {
        CartId id = CartId.create();

        Optional<Cart> optionalCart = repository.findById(id);

        assertThat(optionalCart).isEmpty();
    }

    @Test
    void givenACart_whenRemove_thenItIsRemoved() {
        Cart cart = Cart.create(CartId.create());

        repository.save(cart);
        repository.remove(cart);

        assertThat(repository.findById(cart.getId())).isEmpty();
    }

    @Test
    void givenACartWhichIsNotInTheRepository_whenRemove_thenNoErrorIsThrown() {
        Cart cart = Cart.create(CartId.create());

        repository.remove(cart);

        assertThat(repository.findById(cart.getId())).isEmpty();
    }
}