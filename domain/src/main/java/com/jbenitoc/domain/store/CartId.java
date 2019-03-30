package com.jbenitoc.domain.store;

import com.jbenitoc.domain.ValueObject;

import java.util.UUID;

import static java.util.UUID.randomUUID;

public final class CartId extends ValueObject<UUID> {

    private CartId(UUID id) {
        super(id);
    }

    public static CartId create(UUID id) {
        assertValid(id);
        return new CartId(id);
    }

    public static CartId create() {
        return create(randomUUID());
    }

    private static void assertValid(UUID id) {
        if (id == null) {
            throw new CartIdIsNotValid();
        }
    }
}
