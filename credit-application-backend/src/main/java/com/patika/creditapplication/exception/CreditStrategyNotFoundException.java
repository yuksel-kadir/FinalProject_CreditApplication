package com.patika.creditapplication.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CreditStrategyNotFoundException extends RuntimeException {

    private final String message;
    private final int httpStatus;

    public CreditStrategyNotFoundException() {
        this.message = "Couldn't find a credit strategy.";
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
