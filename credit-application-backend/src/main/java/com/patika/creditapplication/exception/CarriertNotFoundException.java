package com.patika.creditapplication.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CarriertNotFoundException extends RuntimeException {

    private final String message;

    private final int httpStatus;

    public CarriertNotFoundException() {
        this.message = "Carrier not found.";
        httpStatus = HttpStatus.NOT_FOUND.value();
    }
}
