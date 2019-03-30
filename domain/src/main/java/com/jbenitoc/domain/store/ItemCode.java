package com.jbenitoc.domain.store;

import com.jbenitoc.domain.ValueObject;

public final class ItemCode extends ValueObject<String> {

    private ItemCode(String code) {
        super(code);
    }

    public static ItemCode create(String code) {
        assertValid(code);
        return new ItemCode(code);
    }

    private static void assertValid(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new ItemCodeIsNotValid();
        }
    }
}
