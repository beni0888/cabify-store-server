package com.jbenitoc.infrastructure.rest;

import com.jbenitoc.domain.store.CartDoesNotExist;
import com.jbenitoc.domain.store.ItemDoesNotExist;
import com.jbenitoc.infrastructure.rest.dto.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CartDoesNotExist.class, ItemDoesNotExist.class})
    public ResponseEntity<Object> handleNotFoundError(RuntimeException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
