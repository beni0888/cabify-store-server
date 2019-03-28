package com.jbenitoc.model.store;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemCodeTest {

    @Test
    void givenNullCode_whenCreate_thenItThrowsAnException() {
        Exception e = assertThrows(ItemCodeIsNotValid.class, () -> ItemCode.create(null));
        assertThat(e.getMessage()).isEqualTo("ItemCode is not valid, it should be a non-empty string");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void givenEmptyCode_whenCreate_thenItThrowsAnException(String code) {
        Exception e = assertThrows(ItemCodeIsNotValid.class, () -> ItemCode.create(code));
        assertThat(e.getMessage()).isEqualTo("ItemCode is not valid, it should be a non-empty string");
    }

    @Test
    void givenValidCode_whenCreate_thenItWorks() {
        String code = "VOUCHER";

        ItemCode itemCode = ItemCode.create(code);

        assertThat(itemCode.getValue()).isEqualTo(code);
    }
}