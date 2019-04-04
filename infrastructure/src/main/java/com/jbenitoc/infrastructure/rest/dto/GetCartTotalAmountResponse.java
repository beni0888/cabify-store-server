package com.jbenitoc.infrastructure.rest.dto;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public final class GetCartTotalAmountResponse {
    public final String cartId;
    public final BigDecimal totalAmount;
}
