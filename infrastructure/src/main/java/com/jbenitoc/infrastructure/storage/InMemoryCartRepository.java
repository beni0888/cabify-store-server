package com.jbenitoc.infrastructure.storage;

import com.jbenitoc.domain.store.Cart;
import com.jbenitoc.domain.store.CartId;
import com.jbenitoc.domain.store.CartRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public final class InMemoryCartRepository implements CartRepository {

    private Map<CartId, Cart> carts = new ConcurrentHashMap<>();

    @Override
    public void save(Cart cart) {
        carts.put(cart.getId(), cart);
    }

    @Override
    public Optional<Cart> findById(CartId id) {
        return Optional.ofNullable(carts.get(id));
    }

    @Override
    public void remove(Cart cart) {
        carts.remove(cart.getId());
    }
}
