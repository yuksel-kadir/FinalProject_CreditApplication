package com.patika.creditapplication.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorParseStrategyNotFoundException extends RuntimeException {

    private final String message;

    private final int httpStatus;

    public ErrorParseStrategyNotFoundException() {
        this.message = "Error parse strategy not found.";
        httpStatus = HttpStatus.NOT_FOUND.value();
    }
}
