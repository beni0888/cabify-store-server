package com.jbenitoc.model.store;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemNameTest {

    @Test
    void givenNullName_whenCreate_thenItThrowsAnException() {
        Exception e = assertThrows(ItemNameIsNotValid.class, () -> ItemName.create(null));
        assertThat(e.getMessage()).isEqualTo("Item name is not valid, it should be a non-empty string");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void givenEmptyName_whenCreate_thenItThrowsAnException(String name) {
        Exception e = assertThrows(ItemNameIsNotValid.class, () -> ItemName.create(name));
        assertThat(e.getMessage()).isEqualTo("Item name is not valid, it should be a non-empty string");
    }

    @Test
    void givenTooLongName_whenCreate_thenItThrowsAnException() {
        String tooLongName = "AHZqaY14tF8UiU4nKV94n8DU84NziH4u65GVHVhoCcOutpoIvtR5aJd2MwEvK2pA12RFXrGPyOSGS6WbzxVSPKkjdVq0f7jWo" +
                "Sc1wzMRPE4nfhM4AppDdMrh5viwbOMdZbdYHneJmn6OHfEVJUZR2drvbDJMM3JXL8A07LB4ILQ2VNkymnhTLQYkyiMvJIgHf9hhAVx1" +
                "niQC2VnF5oCtCFzqgJHHVWIlhYqGt90C6sSPImL0uTlt2fLJzNwkkTVUSxRoLQuPYENvnwIKIfP25ocCNw7opSrEd7iF01cmqYabkLO" +
                "DWVZYwtc7rhjEliy31dODBsuIWSuq5LRUxe66hCwden1QyzbNxFTRKAvESUzXdebnT9MRItEBZoOSgHaFwRVVuPT3aHWEdLQ5GOzX8i" +
                "nxWo3BCHejfHo1PEQ7f4me3TCOCJDmQ7kPuhhMnHntwErpR7WOk9Krj6eA7K4LTh70HCGSKi64n7cAR1AL6D2lpqVBBmTUK";

        Exception e = assertThrows(ItemNameIsNotValid.class, () -> ItemName.create(tooLongName));
        assertThat(e.getMessage())
                .isEqualTo(String.format("Item name is not valid, its length is higher than maximum allowed [%d]:%n[%s]",
                        ItemName.MAX_LEN, tooLongName));
    }

    @Test
    void givenValidName_whenCreate_thenItWorks() {
        String name = "A pretty cool t-shirt";

        ItemName itemName = ItemName.create(name);

        assertThat(itemName.getValue()).isEqualTo(name);
    }
}