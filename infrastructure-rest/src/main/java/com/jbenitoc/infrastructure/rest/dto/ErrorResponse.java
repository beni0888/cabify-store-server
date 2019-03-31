package com.jbenitoc.infrastructure.rest.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class ErrorResponse {
    public final int status;
    public final String message;
}
