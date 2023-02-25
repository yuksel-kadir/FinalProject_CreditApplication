package com.patika.creditapplication.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CreditApplicationNotFoundException extends RuntimeException {

    private final String message;

    private final int httpStatus;

    public CreditApplicationNotFoundException() {
        this.message = "Credit application not found for the client.";
        httpStatus = HttpStatus.NOT_FOUND.value();
    }
}
