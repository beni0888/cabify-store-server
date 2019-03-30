package com.jbenitoc.domain.store;

public final class ItemNameIsNotValid extends IllegalArgumentException {

    private ItemNameIsNotValid(String message) {
        super(message);
    }

    public static ItemNameIsNotValid createForNullOrEmpty() {
        return new ItemNameIsNotValid("Item name is not valid, it should be a non-empty string");
    }

    public static ItemNameIsNotValid createForTooLong(String name) {
        return new ItemNameIsNotValid(String.format("Item name is not valid, its length is higher than maximum allowed [%d]:%n[%s]", ItemName.MAX_LEN, name));
    }
}
