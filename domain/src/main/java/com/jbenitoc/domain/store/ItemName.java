package com.jbenitoc.domain.store;

import com.jbenitoc.domain.ValueObject;

public final class ItemName extends ValueObject<String> {

    public static final int MAX_LEN = 500;

    private ItemName(String name) {
        super(name);
    }

    public static ItemName create(String name) {
        assertValid(name);
        return new ItemName(name);
    }

    private static void assertValid(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw ItemNameIsNotValid.createForNullOrEmpty();
        }
        if (name.length() > MAX_LEN) {
            throw ItemNameIsNotValid.createForTooLong(name);
        }
    }
}
