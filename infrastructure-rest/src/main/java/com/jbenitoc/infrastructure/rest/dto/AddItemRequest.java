package com.jbenitoc.infrastructure.rest.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public final class AddItemRequest {
    public final String itemCode;
    public final int quantity;
}
