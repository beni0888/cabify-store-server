package com.jbenitoc.domain.store;

import com.jbenitoc.domain.ValueObject;

import java.util.UUID;

import static java.util.UUID.randomUUID;

public final class CartId extends ValueObject<UUID> {

    private CartId(UUID id) {
        super(id);
    }

    private static CartId create(UUID id) {
        assertValid(id);
        return new CartId(id);
    }

    public static CartId create() {
        return create(randomUUID());
    }

    public static CartId create(String id) {
        return create(parseUUID(id));
    }

    private static UUID parseUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            throw new CartIdIsNotValid(id);
        }
    }

    private static void assertValid(UUID id) {
        if (id == null) {
            throw new CartIdIsNotValid(null);
        }
    }
}
