package com.jbenitoc.domain.store;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PriceTest {

    @Test
    void giveNullPrice_whenCreate_thenItThrowsAnException() {
        Exception e = assertThrows(ItemPriceIsNotValid.class, () -> Price.create(null));
        assertThat(e.getMessage()).isEqualTo("Price is not valid, it cannot be null");
    }

    @Test
    void givenNegativePrice_whenCreate_thenItThrowsAnException() {
        Exception e = assertThrows(ItemPriceIsNotValid.class, () -> Price.create(BigDecimal.valueOf(-1.0d)));
        assertThat(e.getMessage()).isEqualTo("Price [-1.000000] is not valid, it cannot be negative");
    }

    @ParameterizedTest
    @MethodSource("createPriceSource")
    void givenValidPrice_whenCreate_thenItWorks(BigDecimal value, BigDecimal expectedValue) {
        Price price = Price.create(value);

        assertThat(price.getValue()).isEqualTo(expectedValue);
    }

    private static Stream createPriceSource() {
        return Stream.of(
                Arguments.of(new BigDecimal("10.5"), new BigDecimal("10.50")),
                Arguments.of(new BigDecimal("10.504"), new BigDecimal("10.50")),
                Arguments.of(new BigDecimal("10.505"), new BigDecimal("10.51"))
        );
    }
}