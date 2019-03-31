package com.jbenitoc.domain.store;

import java.util.Optional;

public interface CartRepository {

    void save(Cart cart);

    Optional<Cart> findById(CartId id);
}
